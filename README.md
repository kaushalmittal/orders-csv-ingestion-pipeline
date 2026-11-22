# Orders CSV Ingestion Pipeline

This Java 17 Maven project has a shared utilities module and three incremental
application modules. Each application module has a reference implementation,
a `*Todo.java` recording scaffold, a sample input, tests, and a runnable app.
Later modules reuse earlier ones:

1. **CSV validation** — `OrderValidationApp` uses `OrderRowValidator`.
2. **Batch routing** — `BatchRoutingApp` reuses the validator and adds
   `BatchDeduplicationRouter`.
3. **Streaming ingestion** — `StreamingCsvIngestor` reuses validation and
   deduplication while processing input incrementally.

`orders-common` contains shared CSV escaping and output-path handling used by
all three applications. Maven builds this dependency automatically as part of
the selected module's reactor build.

The reference classes are available to consult while implementing the matching
TODO scaffolds during recording.

## Build and test

From the repository root, build all modules and run all tests:

```sh
mvn test
```

To build and test just one module and its prerequisites:

```sh
mvn -pl orders-common -am test
mvn -pl csv-parser-validator -am test
mvn -pl dedup-split-router -am test
mvn -pl streaming-resilient-ingestor -am test
```

## Run each module

Build each app and its required modules from the root, then run its main class.
All arguments are optional; with no arguments, each app reads its module's
sample input and writes output under that module's `out/` directory.

### CSV validation

```sh
mvn -pl orders-common -am package
mvn -pl csv-parser-validator -am package
cd csv-parser-validator
java -cp target/classes:../orders-common/target/classes \
  com.example.orders.validation.OrderValidationApp
```

Optional arguments: `input.csv [valid.csv] [invalid.csv]`.

### Batch routing

```sh
mvn -pl dedup-split-router -am package
cd dedup-split-router
java -cp target/classes:../csv-parser-validator/target/classes:../orders-common/target/classes \
  com.example.orders.routing.BatchRoutingApp
```

Optional arguments: `input.csv [clean.csv] [rejected.csv] [KEEP_FIRST|KEEP_LATEST]`.

### Streaming ingestion

```sh
mvn -pl streaming-resilient-ingestor -am package
cd streaming-resilient-ingestor
java -cp target/classes:../dedup-split-router/target/classes:../csv-parser-validator/target/classes:../orders-common/target/classes \
  com.example.orders.ingestion.StreamingCsvIngestor
```

Optional arguments: `input.csv [clean.csv] [rejected.csv] [KEEP_FIRST|KEEP_LATEST] [charset]`.

Each app overwrites its output files on rerun. Use different paths if you want
to preserve prior output.

## Recording sequence

The IntelliJ shared scopes in `.idea/scopes/` provide progressive Project
tool-window views. The shared `orders-common` module appears in each scope.

- Choose **Recording: Validation** to focus on the validation module. Work
  through `OrderRowValidatorTodo`, referring to `OrderRowValidator`, then run
  `OrderValidationApp`.
- Choose **Recording: Batch Routing** to show validation and routing. Work
  through `BatchDeduplicationRouterTodo`, referring to
  `BatchDeduplicationRouter`, then run `BatchRoutingApp`.
- Choose **Recording: Streaming Ingestion** to show all modules. Work through
  `StreamingCsvIngestorTodo`, referring to `StreamingCsvIngestor`, then run the
  end-to-end app.

Memory use in the streaming stage grows with the number of unique order IDs
tracked for deduplication (plus the largest input line), rather than the total
number of input rows.
