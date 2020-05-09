# Example Java in AWS Lambda via API Gateway 

What's that? You like using Java in IntelliJ, and want to set up a simple API callback service on AWS, without all the bells and whistles of setting up the AWS Toolkit, opting instead for a barebones .jar file that will get the job done quick and fast for cheap? Well you've come to the semi-relevant place!

So what's this repo? 
A no-nonsense guide to set up some java code to be triggered via http request, without complicated server setup. It features:

* Java
    * Maven
    * IntelliJ (Community Edition)
* AWS Lambda
    * Run code from an uploaded JAR file.
    * Retrieve request parameters.
    * Set and read system environment variables.
    * CloudWatch Logging.
* API Gateway
    * Hook AWS lambda up to a HTTP API, which is [2-3 times cheaper](https://aws.amazon.com/api-gateway/pricing/) than a REST API configuration.
    

This assumes you have:

* Java and IntelliJ (Community Edition)
* AWS account (a free one will do)

## Step 1: Java + Maven Repository

That's this repository. You can just clone this and skip everything up to the last 3 or so steps.

* IntelliJ: File (Alt-F) -> New... (N) -> Project...

* Create a new Maven project

    * Maven
    * [Project SDK] 1.8 java version
    
* Set name and location (here's the setup for this repo)

    * [Name] aws-gateway-lambda
    * [GroupId] com.flashkirby.examples
    * [ArtifactID] aws-gateway-lambda
    
* The project should initialise with a basic Maven set up. 

* Add the MANIFEST.MF for build properties

* Create the main class that will handle the request event. In this repo, this is the Endpoint.java file

    * The method, `handleRequest`, takes information from the request and the lambda context, and returns a response.

---
    
* Add the Maven dependencies and build information to the pom.xml

    * Right click the pom.xml file and Add as Maven Project
    * Maven -> Reimport 
    
* Maven: aws-gateway-lambda -> Lifecycle -> install

    * Open up Maven from the (default) right sidebar
    
* Jar file will be compiled in the `/target` folder.

## Step 2: AWS Lambda

* Visit [AWS Lambda](https://console.aws.amazon.com/lambda/home)

* Create function
    * Author from scratch
    * [Function name] gateway-lambda-example
    * [Runtime] Java 8
    * [Permissions] Create a new role from AWS Policy Templates
    * [Role name] example-role-lambda
    * [Policy templates - optional] Basic Lambda@Edge permissions (for CloudFront trigger) 
        * Grants permissions to use CloudWatch for logging. You can ignore this if you don't want logs.
        
* Set Code entry type to "Upload a .zip or .jar file"
    * [Function Package] Upload -> the jar file in your `/target` folder.
    
* Set the handler to call the correction function
    * [Handler] `com.flashkirby.examples.awslambda.Endpoint::handleRequest`
    
* Set environment variables for this lambda deployment
    * Environment variables -> Edit
    * Add environment variable
    * [Key/Value] TOKEN/secret
    * Save
    
* Perform a test against the code by configuring a test event (Next to Save). Here's an example of a HTTP query resembling `/gateway-lambda-example?token=hello`
    * For an in depth look at the structure of request, see the "Version 2.0" heading in [this example](https://aws.amazon.com/blogs/compute/building-better-apis-http-apis-now-generally-available).
```
{
    "version": "2.0", 
    "rawPath": "/gateway-lambda-example", 
    "queryStringParameters":
    {
        "token": "hello"
    }
}
```

## Step 3: AWS API Gateway

* \+ Add Trigger (from the Designer)

* [Trigger Configuration] Set API Gateway

* Create an API

* HTTP API
    * [for reasons](https://pages.awscloud.com/Introducing-HTTP-APIs-A-Better-Cheaper-Faster-Way-to-Build-APIs_2020_0304-SRV_OD.html), or if tl/dr...
    * faster: "60% latency reduction" (p99 < 10ms)
    * cheaper: "~70% cost savings"
        * < 300 Million, $3.50 -> $1.00
        * 20 Billion +,$1.51 -> $0.90
        * "Simpler UI + CORS config"

* [Security] Open
    * Read more about JWT auth [here](https://docs.aws.amazon.com/apigateway/latest/developerguide/http-api-jwt-authorizer.html)

* Additional Settings, you can set the name of your API here

* From [API Gateway](https://console.aws.amazon.com/apigateway/home), navigate to your shiny new API

* Call the function with the link in Invoke Url
    * Develop -> Routes, to edit the Method and Path of the function being called.
    * To test non-GET functions, try [Postman](https://www.postman.com/)
    

# Additional Resources

Change environment variables to API Gateway staging variables [[link]](https://aws.amazon.com/blogs/compute/using-api-gateway-stage-variables-to-manage-lambda-functions/)