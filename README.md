# Reproducer for changed behaviour with QuarkusTests + TestProfiles with 3.22.0

## Info for reproducer
- Generated via https://code.quarkus.io/
- Use Extension preset "Event driven service with Kafka"
- Changed:
  - Copy `MyMessagingApplicationTest` to `CopyOfMyMessagingApplicationTest`
  - Add `@QuarkusTestProfile` to `CopyOfMyMessagingApplicationTest`
  - Set fixed port for Kafka-Devservice (`quarkus.kafka.devservices.port=9092`)

Works with 3.21.4, but fails with 3.22.0

## How to reproduce

1. Execute `mvnw clean verify` to run the tests
2. Test fail because of port collision in Kafka-DevService

## Difference between 3.21.4 and 3.22.0
With 3.21.4 the execution order is:
1. `[INFO] Running org.acme.MyMessagingApplicationTest`
2. Start Quarkus + Testcontainers
3. `[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 10.29 s -- in org.acme.MyMessagingApplicationTest`
4. `[INFO] Running org.acme.CopyOfMyMessagingApplicationTest`
5. `INFO  [io.quarkus] (main) code-with-quarkus stopped in 0.556s`
6. Start Quarkus + Testcontainers
7. `[INFO] Tests run: 1, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 8.975 s -- in org.acme.CopyOfMyMessagingApplicationTest`
8. `INFO  [io.quarkus] (main) code-with-quarkus stopped in 0.406s`

With 3.22.0 the execution order is:
1. Start Quarkus + Testcontainers
2. Start Quarkus + Testcontainers

(Only if ports aren't fixed:)
3. `[INFO] Running org.acme.MyMessagingApplicationTest`
4. `[INFO] Running org.acme.CopyOfMyMessagingApplicationTest`
5. `INFO  [io.quarkus] (main) code-with-quarkus stopped in 0.463s`
6. `INFO  [io.quarkus] (main) code-with-quarkus stopped in 0.458s`