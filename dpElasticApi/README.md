# dpElasticApi
The __dpElasticApi__ module encapsulates the HTTP RestClient interaction with ElasticSearch&trade; in to a single module, that isolates the 
client from the need to understand the http interface for ElasticSearch&trade;. This API framework could be extended to work across all protocols, but currently it is limited to the HTTP `RestClient` that AWS Elastic provides.

There are three main entry points into the module;
* `LoadBuilder`<br/>
 Loads a document from Elastic&trade; based on the `index/type/id` 
* `SearchBuilder`<br/>
 Executes a Search against Elastic&trade; based on the `Query` used (_currently only the Term Query is supported_)
* `UpdateBuilder`<br/>
 Updates an existing document with the contents provide, `Upserts` are available if document is missing using the upsert object.
 
## Usage
### `LoadBuilder`
To load a specific document from an `ElasticSearch` &trade; server, you need to build the state of a`LoadBuilder` instance 
and then create the request (`Request`) using the `build()` methods.
 ```java
      Request request = new LoadBuilder().setIndex("ons")
                                          .setType("document")
                                          .setId("data12345")
                                          .setElasticClient(elasticClient)
                                          .build();

```
### `SearchBuilder`
To search for documents in the `ElasticSearch`&trade; server, you need to build up a query `Query` and then create the 
request (`Request`) using the `SearchBuilder`.

 ```java
 
         TermQuery query = new TermQuery("_id",
                                         new TermQueryOptions().setValue("data12345")
                                                               .setBoost(1.2f)); //Currently Boost is irreleant as multiple query support not implemented


        Request request = new SearchBuilder().setQuery(query)
                                           .addStoredField("metadata.createdDate")
                                           .addStoredField("metadata.createdBy")
                                           .setSize(10)
                                           .setFrom(10)
                                           .setElasticClient(elasticClient)
                                           .build();

```

### `UpdateBuilder`
The `UpdateBuilder` will invoke the `ElasticSearch`&trade; update request, if the upsert `Map<String,Object>` is set then the 
request will create the document if one is not present when updating.

```java
 final Map<String, Object> updateDoc = new HashMap();
 final Map<String, Object> upsertDoc = new HashMap();
...
//Create the update and upsert documents
...
        final Request request = new UpdateBuilder().setIndex("ons")
                                                 .setType("document")
                                                 .setId("data12345")
                                                 .setElasticClient(elasticClient)
                                                 .setDoc(updateDoc)
                                                 .setUpsert(upsertDoc)
                                                 .build();

```

