package com.example.orders.ingestion;

import com.example.orders.routing.BatchDeduplicationRouter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Path;

/**
 * Standalone recording scaffold for a streaming ingestion pipeline.
 *
 * <p>Implement the marked method as the final stage of the recording exercise.</p>
 */
public class StreamingCsvIngestor {

    public record StreamMetrics(
            long linesRead,
            long validWritten,
            long duplicatesRejected,
            long malformedRejected,
            long replacementCharsDetected
    ) {}

    /**
     * TODO (Recording concept 3): Decode with replacement for malformed input,
     * remove an optional BOM, and read one line at a time. Delegate row validation
     * and deduplication to the earlier concepts, writing clean and rejected rows
     * directly to UTF-8 sinks. Return accurate metrics without buffering the file.
     */
    public StreamMetrics ingestLargeFile(
            Path sourceCsv,
            Path validSinkCsv,
            Path rejectedSinkCsv,
            Charset sourceCharset
    ) throws IOException {
        throw new UnsupportedOperationException("Implement the streaming pipeline");
    }

    public StreamMetrics ingestLargeFile(
            Path sourceCsv,
            Path validSinkCsv,
            Path rejectedSinkCsv,
            Charset sourceCharset,
            BatchDeduplicationRouter.RetentionPolicy retentionPolicy
    ) throws IOException {
        throw new UnsupportedOperationException("Implement the streaming pipeline");
    }

    public static void main(String[] args) throws IOException {
        //TODO (Recording concept 3): Implement a main method that invokes the ingestLargeFile method
    }
}
