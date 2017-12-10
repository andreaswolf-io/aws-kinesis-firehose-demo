package io.andreaswolf.aws.simple.service;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehose;
import com.amazonaws.services.kinesisfirehose.AmazonKinesisFirehoseClientBuilder;
import com.amazonaws.services.kinesisfirehose.model.PutRecordRequest;
import com.amazonaws.services.kinesisfirehose.model.Record;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;

/**
 * Service for testing a simple put into a aws kinesis firehose stream.
 * <p>
 * Define {@code aws.accessKeyId, aws.secretKey} with your AWS IAM credentials and {@code aws.firehose.stream.name} with the naem of your stream
 * in application.properties
 *
 * @author Andreas Wolf
 */
@Component
public class KinesisService {

    @Value("${aws.firehose.stream.name}")
    private String streamName;

    @Value("${aws.accessKeyId}")
    private String accessKey;

    @Value("${aws.secretKey}")
    private String secretKey;

    /**
     * Sends a sample firehose stream.
     */
    public void sendFirehoseStream() {

        BasicAWSCredentials credentials = new BasicAWSCredentials(accessKey, secretKey);
        AWSCredentialsProvider credentialsProvider = new AWSStaticCredentialsProvider(credentials);

        //build firehose client
        AmazonKinesisFirehose firehoseClient = AmazonKinesisFirehoseClientBuilder.standard()
                .withRegion(Regions.EU_CENTRAL_1)
                .withCredentials(credentialsProvider)
                .build();

        //create PUT record and define delivery stream
        PutRecordRequest putRecordRequest = new PutRecordRequest();
        putRecordRequest.setDeliveryStreamName(streamName);

        //data
        String data = "{\"user\":1234}" + "\n";

        ByteBuffer buffer = ByteBuffer.allocate(data.length());
        buffer.wrap(data.getBytes(Charset.forName("UTF-8")));

        Record record = new Record().withData(buffer);
        putRecordRequest.setRecord(record);

        // Put record into the DeliveryStream
        firehoseClient.putRecord(putRecordRequest);

        firehoseClient.shutdown();
    }
}
