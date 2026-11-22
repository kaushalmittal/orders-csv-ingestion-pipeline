package com.example.orders.ingestion;

import com.example.orders.routing.BatchDeduplicationRouter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class StreamingCsvIngestorTest {

    @Test
    void streamsBomAndMalformedBytesWithoutCrashing(@TempDir Path dir) throws IOException {
        Path input = dir.resolve("corrupt.csv");
        byte[] headerAndValid = "\ufefforderId,customerEmail,amount,createdAt\n"
                .concat("ORD-1,ok@example.com,\"1,200.00\",2026-11-22T08:00:00Z\n")
                .getBytes(StandardCharsets.UTF_8);
        byte[] badBytes = new byte[]{(byte) 0xFF, (byte) 0xFE, (byte) '\n'};
        byte[] trailingValid = "ORD-2,ok2@example.com,55.00,2026-11-22T08:05:00Z\n"
                .getBytes(StandardCharsets.UTF_8);
        byte[] combined = new byte[headerAndValid.length + badBytes.length + trailingValid.length];
        System.arraycopy(headerAndValid, 0, combined, 0, headerAndValid.length);
        System.arraycopy(badBytes, 0, combined, headerAndValid.length, badBytes.length);
        System.arraycopy(trailingValid, 0, combined, headerAndValid.length + badBytes.length, trailingValid.length);
        Files.write(input, combined);

        var metrics = new StreamingCsvIngestor().ingestLargeFile(
                input,
                dir.resolve("valid.csv"),
                dir.resolve("rejected.csv"),
                StandardCharsets.UTF_8
        );

        assertEquals(2L, metrics.validWritten());
        assertEquals(1L, metrics.malformedRejected());
        assertTrue(metrics.replacementCharsDetected() > 0);
    }

    @Test
    void connectsValidationDeduplicationAndOutputStages(@TempDir Path dir) throws IOException {
        Path input = dir.resolve("orders.csv");
        Files.writeString(input, """
                orderId,customerEmail,amount,createdAt
                ORD-1,a@example.com,10.00,2026-11-22T08:00:00Z
                ORD-1,a@example.com,20.00,2026-11-22T09:00:00Z
                ORD-2,invalid,-5.00,not-a-date
                """, StandardCharsets.UTF_8);
        Path clean = dir.resolve("out/clean.csv");
        Path rejected = dir.resolve("out/rejected.csv");

        var metrics = new StreamingCsvIngestor().ingestLargeFile(
                input,
                clean,
                rejected,
                StandardCharsets.UTF_8,
                BatchDeduplicationRouter.RetentionPolicy.KEEP_LATEST
        );

        assertEquals(1L, metrics.validWritten());
        assertEquals(1L, metrics.duplicatesRejected());
        assertEquals(1L, metrics.malformedRejected());
        assertTrue(Files.readString(clean).contains("ORD-1,a@example.com,20.00,2026-11-22T09:00:00Z"));
        String rejectedContents = Files.readString(rejected);
        assertTrue(rejectedContents.contains("SUPERSEDED_DUPLICATE"));
        assertTrue(rejectedContents.contains("INVALID_EMAIL"));
    }
}
