# Quarkus AVRO Native Example

This project provides example code for [AVRO](https://avro.apache.org/) serialization
in [quarkus native](https://quarkus.io/guides/building-native-image) applications for both binary and JSON formats.

## Build and run JAR

```shell
mvn package 
java -jar ./target/quarkus-app/quarkus-run.jar
```

## Build and run native binary

**Prerequisites**

* [GraalVM Configured](https://quarkus.io/guides/building-native-image#configuring-graalvm)
    * `native-image` installed

```shell
mvn package -Pnative 
./target/quarkus-avro-native-example-1.0.0-SNAPSHOT-runner
```