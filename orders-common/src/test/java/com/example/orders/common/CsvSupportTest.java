package com.example.orders.common;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CsvSupportTest {

    @Test
    void escapesCsvValuesOnlyWhenNeeded() {
        assertEquals("plain", CsvSupport.escape("plain"));
        assertEquals("\"a,b\"", CsvSupport.escape("a,b"));
        assertEquals("\"say \"\"hi\"\"\"", CsvSupport.escape("say \"hi\""));
        assertEquals("", CsvSupport.escape(null));
    }

    @Test
    void stripsLeadingBomAndRecognizesSupportedOrderHeaders() {
        assertEquals("orderId,customerEmail,amount,createdAt",
                CsvSupport.stripLeadingBom("\uFEFForderId,customerEmail,amount,createdAt"));
        assertEquals("unchanged", CsvSupport.stripLeadingBom("unchanged"));
        assertTrue(CsvSupport.isOrderHeader("orderId,customerEmail,amount,createdAt"));
        assertTrue(CsvSupport.isOrderHeader("\"orderId\",\"customerEmail\",\"amount\",\"createdAt\""));
        assertFalse(CsvSupport.isOrderHeader("not,a,header,row"));
    }

    @Test
    void formatsOrderFieldsAsCsv() {
        assertEquals("ORD-1,\"a,b@example.com\",12.50,2026-11-22T08:00:00Z",
                CsvSupport.formatOrderRow(
                        "ORD-1",
                        "a,b@example.com",
                        new BigDecimal("12.50"),
                        Instant.parse("2026-11-22T08:00:00Z")));
    }
}
