# Batch Deduplication & Routing

Run `BatchRoutingApp` to validate, deduplicate, and route the module's sample
batch. It reuses the CSV validation module. Implement
`BatchDeduplicationRouterTodo` during recording, using
`BatchDeduplicationRouter` as the reference.

From the repository root:

```sh
mvn -pl dedup-split-router -am test
mvn -pl dedup-split-router -am package
cd dedup-split-router
java -cp target/classes:../csv-parser-validator/target/classes:../orders-common/target/classes \
  com.example.orders.routing.BatchRoutingApp
```

Optional arguments:
`input.csv [clean.csv] [rejected.csv] [KEEP_FIRST|KEEP_LATEST]`.
See the root [README](../README.md) for the full pipeline.
