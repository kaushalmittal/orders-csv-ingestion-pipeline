package com.example.orders.common;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public final class OutputPathSupport {

    private OutputPathSupport() {}

    public static void ensureDistinctPaths(Path... paths) {
        if (paths == null || paths.length < 2) {
            throw new IllegalArgumentException("At least two paths are required");
        }

        Set<Path> normalizedPaths = new HashSet<>();
        for (Path path : paths) {
            Path normalizedPath = Objects.requireNonNull(path, "path").toAbsolutePath().normalize();
            if (!normalizedPaths.add(normalizedPath)) {
                throw new IllegalArgumentException("Paths must be different");
            }
        }
    }

    public static void createParentDirectories(Path... outputPaths) throws IOException {
        for (Path outputPath : outputPaths) {
            Path parent = Objects.requireNonNull(outputPath, "outputPath")
                    .toAbsolutePath()
                    .normalize()
                    .getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
        }
    }
}
