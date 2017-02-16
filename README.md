dp-publish-pipeline-enrich-search
================
#Digital Publishing Document Enrichment
This microservice reads the resource location or content from an message on a Kafka topic, it then converts either the file based 
 resource or message content on the request in to plain text. The original document is then loaded from ElasticSearch and the content is added (enriched) and the document save.
 
If the load from Elastic Search fails or is the Update document fails then the load-enrich-save process will be retried for 15 attempts finally it will fail with an error message in the logs.
If any requests are inflight during a failure then the requests will be retained on the kafka and resumed when the server restarts (i.e. the messages are not acknowledged until after they are processed).
This does mean that there is a possibility of a messages/request being process twice, due to the idempotent nature of this service and ElasticSearch&trade; this will result in the same outcome as if the message was only processed once.
 
This is a multithreaded consumer of the kafka queues and this micro-service may have multiple instances running, as each 
request contains a single resource/content for a single ElasticSearch document/Web-page, it is possible that two requests 
to update the same document with different content. To remove the possibility of loosing data this concurrent raise condition 
is resolved through optimistic locking of the page. If between reading and saving the document the page will be reloads, 
re-enriched, re-saved .

![Kafka->Transform-Extract->Load->Enrich->Save](EnrichmentFlow.png)
 
Currently all invocations are via kafka, but a REST api could easily be added using the http end point and adding at webserver to the boot configuration.

##Kafka messages requests
By default this micro-service listens to a single kafka topic `dp.enrichment` and then uses the wrapping object to determine the next step.

 
###Enrich a single resources
Currently we are using a single 'non-wrapped' json request that has two purposes, enrich page based on a filebased resource
and enrich based on content of the message.

Enrich PageData based on the content of the message is received as:
```
{
  "fileContent" : "json:fileContent",
  "filelocation" : "/blah/blah/blah/XXXXX.json"
}
``` 

Enrich Downloads based on a File is received as:
```
{
  "s3Location" : "s3://s3Loc",
  "filelocation" : "/blah/blah/blah/"
}
```


Although this is not ideal, this multi-purposed messages is limited to the Kafka and Transformation service, the 
TransformationService emits a `UpdatePageDataPayload` and `UpdateResourcePayload` to clearly signal the purpose of each
 request and enable polymorphic services to provide different actions based on the payload


##Running
To run the application locally run the `./run.sh` in the root directory, this will initiates a Maven&trade; clean rebuild (`mvn clean install`).
The Kafka consumer is listening to a inbound topic of `dp.enrichment` 

`run.sh` expects you to have a '~/.aws/configuration/credentials' that contains the following variables if you wish to 
connect to S3 for the file resources
```properties
[default]
aws_access_key_id = X2C£FS$FNOTAREALKEY	
aws_secret_access_key = khj123j8sfdjk12Not4AR34lK3y
```
If you do not have this configuration then you can set the environment variables manually before running
```bash
export S3_SECRET_ACCESS_KEY=X2C£FS$FNOTAREALKEY
export S3_ACCESS_KEY=khj123j8sfdjk12NotARealKey
```


### Configuration 
Configuration is all located in a single Yaml file [application.yml](src/main/resources/application.yml)

##Design

This enrichment micro-service is a [Spring Integration&trade;](https://projects.spring.io/spring-integration/) hosting on a (non-web) [Spring Boot&trade;](http://projects.spring.io/spring-boot/) container.

###Spring integration&trade;
 Spring Integration&trade; was used to separate the implementation of the business logic from the technical and structural wiring of the application.
 For example the business services should not be aware of the message transport layer (or threading model or transaction model or retry model) that is being implemented.
 
 The business service layer should only be concerned with the implementation of the business logic, this `Separation Of Concerns` 
 is aided by the use of the Spring Integration&trade; framework, as the definition of the protocols, transformations and 
 threadpools are defined as part of the framework and not inside any business service.
     
 Thus the current implementation can concentrate on the implementation (and testing) of the services: 
 * _TransformationSerivce_</br>
    Translates the inbound single Json into request specific payload objects
 * _ExtractContentService_</br>
    Converts the PageData or loads and converts the Downloadable content. The output contains the data in a clear text format
 * _DocumentLoaderService_<br/> 
    Loads the page page from the Elastic Search
 * _EnrichmentService_<br/>
    Added the 'Data' to the approapriate location on the Page
 * _UpsertService_<br/>
    Updates the record in elastic, if the record doesn't exist or if it has changed since loading an exception is thrown and the flow reverts back to the _DocumentLoaderService_  

The current integration flow is above but this could easily be amended to include other inbound and outbound endpoints, primarily by only be including new configuration.
![Spring Integration Flow -> Kafka|JMS|SOAP|Rest -> transform -> load -> enrich -> upsert](multiInboundEndpoint.png)
>These are only examples are not the current flow (See above for the current flow)

###Spring Boot&trade;
We are using Spring Boot&trade; with out a Webservice as this creates an easy to manage deployment artifact with
 (we have experienced classloader issues with uber-jar/jar-with-dependencies, that we have not with Spring Boots specialise classloader).

Currently we are building an executable jar file that can be executed directly, the AWS Keys are requirement to be exposed as variable 
    

## Appendix
###Polymorphic Messaging via Json Wrapper Objects
The following may be removed when current Pipeline prockafkaInboundEndpoint.png
                                                       loadCredentials.sh
                                                       multiInboundEndpoint.png
                                                       pom.xml
                                                       run.shess has been confirmed
But the Jackson configuration is set up to accept different messages over the same inbound topic, but use a Json wrapping
 object, that Jackson can use to enable polymorphic messaging over the same inbound topic; i.e if required at a later date
 the inbound requests could be deleteRequest, indexCollectionRequest, etc... have a look at [Request.java](./src/main/java/com/github/index/enrichment/model/Request.java) 
Currently the `Request` _interface_ is not configured in the but if it was it would build the correct request instance based on the message supplied.
(*NB* you would also need to use the [SubTypeMapper](./src/main/java/com/github/onsdigital/index/enrichment/model/transformer/SubTypeMapper.java) to register all the subtypes of `Request` with the `ObjectMapper`)

Spring Integration would then invoke the correct method on the [DocumentLoaderService](./src/main/java/com/github/onsdigital/index/enrichment/model/transformer/SubTypeMapper) based on the type of Request

> If you where to implement more polymorphic solutions then it would make sense to replace the direct invocation of the service object from 
> Sprint Integration&trade; with a _facade_ which could then delegate to the correct service.
> You could then implement, for example, a `DatabaseService`, `ElasticSearchService` and a `S3Service` all fronted be a `DocumentLoaderFacade`
>

#### Below are example of message that were available prior to the Pipeline interface definition.
_The following comment and the associated code should be removed
To enrich an explicitly defined  resources from the repository you can request an individual resources to be enriched.
```
{
  "EnrichDocumentsRequest": {
    "resources": [
      {
        "id": "/economy/investmentspensionsandtrusts/datasets/ownershipofukshares/2014",
        "index": "ons",
        "type": "dataset"
      }
    ]
  }
}
```

###Enrich a whole index
To enrich a whole index then send an `EnrichAllDocumentsRequest` on the same kafka topic `dp.enrichment` and all the resources with in the index will be enriched
```
{
  "EnrichAllDocumentsRequest": {
    "index": "ons"
  }
}
```
By supplying this request the whole "ons" index is loaded and the downloads directory loaded from the localhost file system.
Once the file system is moved to the S3 file system we will need to revisit this

###Enrich a single resource
If the location of the underlying `page.json` file is known (i.e. local file:/classpath:/http:/s3:) then the file can be supplied with the full path for it to be loaded and enriched
```
{
  "EnrichResourceDocumentsRequest": {
    "resources": [
      {
        "dataFileLocation": "s3://ons-web-page/publishing/2017-01-23/master/aboutus/careers/benefits/page.json"
      }
    ]
  }
}
```
 
### Contributing

See [CONTRIBUTING](CONTRIBUTING.md) for details.

### License

Copyright © 2016-2017, Office for National Statistics (https://www.ons.gov.uk)

Released under MIT license, see [LICENSE](LICENSE.md) for details.
