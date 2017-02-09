dp-publish-pipeline-enrich-search
================
#Digital Publishing Document Enrichment
This microservice reads the resources from an index and loads the downloadable resources and the data.json files and extracts the text from the contents and adds this to the index.

Currently all invocations are via kafka, but a REST api could easily be added using the http end point and adding at webserver to the boot configuration.

##Kafka messages requests
By default this micro-service listens to a single kafka topic `dp.enrichment` and then uses the wrapping object to determine the next step.
 
###Enrich a single resources
To enrich a expicitly defined  resources from the repository you can request an individual resources to be enriched.
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
If the location of the underlying `data.json` file is known (i.e. local file:/classpath:/http:/s3:) then the file can be supplied with the full path for it to be loaded and enriched
```
{
  "EnrichResourceDocumentsRequest": {
    "resources": [
      {
        "dataFileLocation": "s3://ons-web-data/publishing/2017-01-23/master/aboutus/careers/benefits/data.json"
      }
    ]
  }
}
```
 

##Design

This enrichment micro-service is a [Spring Integration&trade;](https://projects.spring.io/spring-integration/) hosting on a (non-web) [Spring Boot&trade;](http://projects.spring.io/spring-boot/) container.

###Spring integration&trade;
 Spring Integration&trade; was used to separate the implementation of the business logic from the technical and structure wiring of the application.
 For example the business services should not be aware of the message transport layter (or threading model or transaction model) that is being implemented.
 
 The business service layer should only be concerned with the impelmentation of the business logic, this `Separation Of Concerns` is aided by the use of the Spring Integration&trade;
    framework, as the definition of the protocols, transformations and threadpools are defined as part of the framework and not inside any business service.
     
 Thus the current implementation can concentrate on the implementation (and testing) of the services: 
 
 * _DocumentLoaderService_<br/> 
    Loads the page data from the file location whether local, S3 or http
 * _EnrichmentService_<br/>
    Loads the download content and decodes the documents, attaching the ascii text to the index-able document
 * _UpsertService_<br/>
    Updates the record in elastic, if the record doesn't exist we can create an empty record with just the downloads and pageData elements (_we may want to change this_)  

The current integration flow is
![Spring Integration Flow -> Kafka -> transform -> load -> enrich -> upsert](kafkaInboundEndpoint.png)

but this could easily ber ended to include other inbound and outbound endpoints, primarily by only be including new configuration.
![Spring Integration Flow -> Kafka|JMS|SOAP|Rest -> transform -> load -> enrich -> upsert](multiInboundEndpoint.png)

###Spring Boot&trade;


### Contributing

See [CONTRIBUTING](CONTRIBUTING.md) for details.

### License

Copyright © 2016-2017, Office for National Statistics (https://www.ons.gov.uk)

Released under MIT license, see [LICENSE](LICENSE.md) for details.
