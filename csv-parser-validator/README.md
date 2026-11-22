# CSV Parsing & Validation

Run `OrderValidationApp` to validate the module's sample CSV. Implement
`OrderRowValidatorTodo` during recording, using `OrderRowValidator` as the
reference. This is the first module in the pipeline.

From the repository root:

```sh
mvn -pl csv-parser-validator -am test
mvn -pl csv-parser-validator -am package
cd csv-parser-validator
java -cp target/classes:../orders-common/target/classes \
  com.example.orders.validation.OrderValidationApp
```

Optional arguments: `input.csv [valid.csv] [invalid.csv]`. See the root
[README](../README.md) for the later modules.
