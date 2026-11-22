package com.example.orders.common;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

public final class CsvSupport {

    public static final String ORDER_HEADER = "orderId,customerEmail,amount,createdAt";

    private CsvSupport() {}

    public static String stripLeadingBom(String value) {
        Objects.requireNonNull(value, "value");
        return !value.isEmpty() && value.charAt(0) == '\uFEFF' ? value.substring(1) : value;
    }

    public static boolean isOrderHeader(String line) {
        Objects.requireNonNull(line, "line");
        return line.startsWith(ORDER_HEADER) || line.startsWith("\"orderId\",");
    }

    public static String formatOrderRow(
            String orderId,
            String customerEmail,
            BigDecimal amount,
            Instant createdAt
    ) {
        Objects.requireNonNull(amount, "amount");
        Objects.requireNonNull(createdAt, "createdAt");
        return escape(orderId) + ","
                + escape(customerEmail) + ","
                + amount.toPlainString() + ","
                + createdAt;
    }

    public static String escape(String value) {
        if (value == null) {
            return "";
        }
        boolean needsQuotes = value.indexOf(',') >= 0 || value.indexOf('"') >= 0
                || value.indexOf('\n') >= 0 || value.indexOf('\r') >= 0;
        String escaped = value.replace("\"", "\"\"");
        return needsQuotes ? "\"" + escaped + "\"" : escaped;
    }
}
