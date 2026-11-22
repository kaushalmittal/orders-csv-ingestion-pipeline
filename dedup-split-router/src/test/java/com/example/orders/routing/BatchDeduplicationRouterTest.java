package com.example.orders.routing;

import com.example.orders.validation.OrderRowValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BatchDeduplicationRouterTest {

    @Test
    void keepsLatestAndReturnsTheSupersededRowForRejection() {
        var validator = new OrderRowValidator();
        var router = new BatchDeduplicationRouter(BatchDeduplicationRouter.RetentionPolicy.KEEP_LATEST);

        var first = validator.validateRow(2, "ORD-1,a@example.com,10.00,2026-11-22T08:00:00Z");
        var latest = validator.validateRow(3, "ORD-1,a@example.com,20.00,2026-11-22T09:00:00Z");

        assertTrue(router.accept(first).isEmpty());
        var rejected = router.accept(latest).orElseThrow();

        assertEquals(2, rejected.lineNumber());
        assertEquals("SUPERSEDED_DUPLICATE", rejected.category());
        assertEquals(1, router.duplicateCount());
        assertEquals(latest, router.winners().get(0));
    }

    @Test
    void keepsFirstWhenConfigured() {
        var validator = new OrderRowValidator();
        var router = new BatchDeduplicationRouter(BatchDeduplicationRouter.RetentionPolicy.KEEP_FIRST);
        var first = validator.validateRow(2, "ORD-1,a@example.com,10.00,2026-11-22T08:00:00Z");
        var later = validator.validateRow(3, "ORD-1,a@example.com,20.00,2026-11-22T09:00:00Z");

        router.accept(first);

        assertEquals(later.rawLine(), router.accept(later).orElseThrow().rawLine());
        assertEquals(first, router.winners().get(0));
    }
}
