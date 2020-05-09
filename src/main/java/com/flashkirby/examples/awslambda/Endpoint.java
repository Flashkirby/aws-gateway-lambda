package com.flashkirby.examples.awslambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedHashMap;
import java.util.Map;

public class Endpoint {

    /***
     * https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
     * @param event the request that comes in
     * @param context of the lambda deployment
     * @return a response
     */
    public String handleRequest(Map<String,Object> event, Context context) {
        // The logger connected to CloudWatch
        LambdaLogger logger = context.getLogger();

        // Get a variable from the lambda ENVIRONMENT
        String expected = System.getenv("TOKEN");

        // Read the query parameters passed in by the query
        LinkedHashMap<String, String> params = (LinkedHashMap<String, String>) event.get("queryStringParameters");
        String token = String.valueOf(params.get("token"));

        logger.log("Comparing received: '" + token + "' with '" + expected + "'");
        
        if (token.equals(expected)) {
            return "Have a cookie!";
        }

        return "Hello world...";
    }
}
