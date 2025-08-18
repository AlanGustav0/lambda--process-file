package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;

public class ExecutionHandler implements RequestHandler<S3Event,String> {

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);

        String bucketName = record.getS3().getBucket().getName();
        String objectKey = record.getS3().getObject().getKey();

        System.out.println("Bucket: " + bucketName);
        System.out.println("Objeto: " + objectKey);

        return "Success!";
    }
}
