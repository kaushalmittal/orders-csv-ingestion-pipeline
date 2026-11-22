# Streaming CSV Ingestion

Run `StreamingCsvIngestor` to stream the module's malformed-input sample
through validation, deduplication, and clean/rejected output. It reuses the
validation and batch-routing modules. Implement `StreamingCsvIngestorTodo`
during recording, using `StreamingCsvIngestor` as the reference.

From the repository root:

```sh
mvn -pl streaming-resilient-ingestor -am test
mvn -pl streaming-resilient-ingestor -am package
cd streaming-resilient-ingestor
java -cp target/classes:../dedup-split-router/target/classes:../csv-parser-validator/target/classes:../orders-common/target/classes \
  com.example.orders.ingestion.StreamingCsvIngestor
```

Optional arguments:
`input.csv [clean.csv] [rejected.csv] [KEEP_FIRST|KEEP_LATEST] [charset]`.
See the root [README](../README.md) for the incremental module sequence.
