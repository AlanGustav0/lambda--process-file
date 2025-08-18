package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;
import com.amazonaws.services.lambda.runtime.events.models.s3.S3EventNotification;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.GetObjectRequest;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class ExecutionHandler implements RequestHandler<S3Event,String> {

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        S3EventNotification.S3EventNotificationRecord record = s3Event.getRecords().get(0);

        String bucketName = record.getS3().getBucket().getName();
        String objectKey = record.getS3().getObject().getKey();

        AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                .withRegion(Regions.US_EAST_1)
                .build();

        try{
            S3Object s3Object = s3Client.getObject(bucketName,objectKey);
            S3ObjectInputStream inputStream = s3Object.getObjectContent();

            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            System.out.println("INICIANDO PROCESSAMENTO DO ARQUIVO");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            System.out.println("ARQUIVO PROCESSADO COM SUCESSO");
        } catch (Exception e) {
            System.out.println("Erro ao processar arquivo" + e);
        }

        return "Success!";
    }
}
