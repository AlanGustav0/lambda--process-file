package org.example;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.S3Event;

public class ExecutionHandler implements RequestHandler<S3Event,String> {

    @Override
    public String handleRequest(S3Event s3Event, Context context) {
        System.out.println(s3Event);
        return "Success!";
    }
}
