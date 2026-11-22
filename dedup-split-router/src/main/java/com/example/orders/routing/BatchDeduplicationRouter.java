package com.example.orders.routing;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Standalone recording scaffold for deduplicating already-validated orders.
 *
 * <p>Its input record represents a valid row produced by Stage 1. Implement the
 * marked methods as the second stage of the recording exercise.</p>
 */
public class BatchDeduplicationRouter {

    public enum RetentionPolicy {
        KEEP_FIRST,
        KEEP_LATEST
    }

    public record ValidatedOrder(
            long lineNumber,
            String rawLine,
            String orderId,
            String customerEmail,
            BigDecimal amount,
            Instant createdAt
    ) {}

    public record RejectedEntry(long lineNumber, String reason, String rawLine) {}

    private final RetentionPolicy policy;

    public BatchDeduplicationRouter(RetentionPolicy policy) {
        this.policy = policy;
    }

    /**
     * TODO (Recording concept 2): Find the current winner for the candidate's ID.
     * Keep the first candidate or compare timestamps for KEEP_LATEST. Return each
     * losing row with its line number and reason, updating the winner as needed.
     */
    public Optional<RejectedEntry> accept(ValidatedOrder candidate) {
        throw new UnsupportedOperationException("Implement order deduplication");
    }

    /**
     * TODO (Recording concept 2): Return the retained winner for each unique ID.
     */
    public List<ValidatedOrder> winners() {
        throw new UnsupportedOperationException("Implement winner retrieval");
    }

    /**
     * TODO (Recording concept 2): Return the number of duplicate rows rejected.
     */
    public long duplicateCount() {
        throw new UnsupportedOperationException("Implement duplicate counting");
    }

    public RetentionPolicy policy() {
        return policy;
    }
}
