package com.flashkirby.examples.awslambda;

import com.amazonaws.services.lambda.runtime.Context;

import java.util.Map;

public class Endpoint {

    /***
     * https://docs.aws.amazon.com/lambda/latest/dg/java-handler.html
     * @param event this is the request that comes in
     * @param context of the lambda deployment
     * @return a response
     */
    public String handleRequest(Map<String,Object> event, Context context) {

        return "Hello World";
    }
}
