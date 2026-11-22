package com.example.orders.validation;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

/**
 * Recording scaffold for CSV tokenization and order-row validation.
 *
 * <p>Implement the marked methods as the first stage of the recording exercise.</p>
 */
public class OrderRowValidator {

    public record OrderRow(String orderId, String customerEmail, BigDecimal amount, Instant createdAt) {}

    public record ValidationIssue(
            long lineNumber,
            String fieldName,
            String ruleCode,
            String detail,
            String rawValue
    ) {}

    public record RowValidationResult(
            long lineNumber,
            String rawLine,
            OrderRow record,
            List<ValidationIssue> issues
    ) {
        public boolean isValid() {
            return record != null && issues.isEmpty();
        }
    }

    /**
     * TODO (Recording concept 1, part 1): Split only on commas outside quotes,
     * decode doubled quotes ("") as a literal quote, and reject unclosed quotes.
     */
    public List<String> tokenizeCsvLine(String line) {
        throw new UnsupportedOperationException("Implement CSV tokenization");
    }

    /**
     * TODO (Recording concept 1, part 2): Require four columns; validate order ID,
     * email, positive decimal amount, and ISO-8601 timestamp independently. Collect
     * every issue. Return a record only when the issue list is empty.
     */
    public RowValidationResult validateRow(long lineNumber, String rawLine) {
        throw new UnsupportedOperationException("Implement row validation");
    }
}
