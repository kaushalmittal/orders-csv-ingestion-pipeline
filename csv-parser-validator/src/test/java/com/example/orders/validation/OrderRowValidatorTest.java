package com.example.orders.validation;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class OrderRowValidatorTest {

    @Test
    void collectsAllFieldErrorsWithoutStoppingAtFirst() {
        OrderRowValidator validator = new OrderRowValidator();
        var result = validator.validateRow(2L, "BAD-1,not-an-email,-10.00,bad-date");

        assertFalse(result.isValid());
        assertEquals(4, result.issues().size());
    }

    @Test
    void parsesQuotedCommasAndEscapedQuotesCleanly() {
        OrderRowValidator validator = new OrderRowValidator();
        var fields = validator.tokenizeCsvLine("ORD-104,\"quoted, \"\"gold\"\" value\",1,2026-11-22T10:15:00Z");
        assertEquals("quoted, \"gold\" value", fields.get(1));

        var result = validator.validateRow(4L, "ORD-104,vip+gold@example.com,\"1,250.50\",2026-11-22T10:15:00Z");

        assertTrue(result.isValid());
        assertEquals("vip+gold@example.com", result.record().customerEmail());
        assertEquals("1250.50", result.record().amount().toPlainString());
    }
}
