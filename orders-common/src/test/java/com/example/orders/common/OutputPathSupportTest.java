package com.example.orders.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class OutputPathSupportTest {

    @Test
    void rejectsPathsThatNormalizeToTheSameLocation(@TempDir Path directory) {
        Path input = directory.resolve("orders.csv");
        Path outputAlias = directory.resolve("nested/../orders.csv");

        assertThrows(IllegalArgumentException.class,
                () -> OutputPathSupport.ensureDistinctPaths(input, outputAlias));
    }

    @Test
    void createsMissingOutputParentDirectories(@TempDir Path directory) {
        Path output = directory.resolve("nested/output.csv");

        assertDoesNotThrow(() -> OutputPathSupport.createParentDirectories(output));
        assertTrue(Files.isDirectory(directory.resolve("nested")));
    }
}
