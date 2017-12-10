# AWS Kinesis Firehose Simple Demo

Shows the simple usage of the AWS Java SDK to send a stream to Kinesis Firehose.

## Prerequisites

* Java 8
* Maven 3
* AWS Account
    * Configure Kinesis Firehose Delivery Stream
    * AWS IAM User

## Configuration

src/main/resources/application.properties

```
aws.accessKeyId: <AWS IAM accessKey>
aws.secretKey: <AWS IAM secret key>
aws.firehose.stream.name: <name of delivery stream>
```

## Run

Run the spring boot application through your favorite IDE or through maven:

```
mvn spring-boot:run
```