## Kafka Quickstart
This is simple application that sends mock temperature events to a kafka cluster. It can send up to about 5000 events per second. More configuration of the cluster and the producer required to achieve higher throughput.

### How To Run
First package the application into a single jar file:
```shell
./mvnw clean package
```

Then run the application:
```shell
java -jar ./target/kafka-quickstart-0.1.jar \
  --kafkaSslDir=<CLIENT_CREDENTIALS_DIR> \
  --kafkaSslPass=<CLIENT_CREDENTIALS_FILE_PASS> \
  --kafkaServers=<YOUR_SERVER> \
  --appSensorsCount=100 \
  --appMeasurementsTopic=<YOUR_TOPIC>
```