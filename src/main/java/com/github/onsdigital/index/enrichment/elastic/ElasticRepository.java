package com.github.onsdigital.index.enrichment.elastic;

import com.github.onsdigital.index.enrichment.model.Data;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by fawks on 01/02/2017.
 */
@Component
public class ElasticRepository {
  private static final Logger LOGGER = LoggerFactory.getLogger(ElasticRepository.class);

  @Autowired
  private TransportClient elasticClient;

  public Data loadData(String id, String index, String type) {

    GetResponse document = elasticClient.get(new GetRequest(index, type, id))
                                        .actionGet();
    return new Data().setId(document.getId())
                     .setIndex(document.getIndex())
                     .setType(document.getType())
                     .setSource(document.getSource());

  }


  public void upsertData(String id, String index, String type, Map<String, Object> updatedSource) {
    UpdateRequestBuilder updateRequestBuilder = elasticClient.prepareUpdate(index, type, id);
    updateRequestBuilder.setDoc(updatedSource);
    UpdateResponse updateResponse = updateRequestBuilder.execute()
                                                        .actionGet();
    LOGGER.info("upsertData([id => {}, index => {}, type => {}, updatedSource]) : ",
                id,
                index,
                type,
                updateResponse.isCreated());

  }

  /**
   * Load all the documents for a reindex
   *
   * @param index
   * @return
   */
  public List<Data> listAllIndexDocuments(String index) {
    LOGGER.info("listAllIndexDocuments([index]) : for index '{}' ", index);



    SearchRequestBuilder query = elasticClient.prepareSearch()
                                              .setIndices(index)
                                              .setScroll(TimeValue.timeValueHours(1))
                                              .setSize(1000);

    LOGGER.info("listAllIndexDocuments([index]) : query '{}'", query);


    SearchResponse searchResponse = query.execute()
                                         .actionGet();

    List<Data> indexedDocuments = new ArrayList<>();

    do {
      Arrays.stream(searchResponse.getHits()
                                  .getHits())
            .forEach(d -> indexedDocuments.add(new Data().setId(d.getId())
                                                         .setIndex(d.getIndex())
                                                         .setType(d.getType())
                                                         .setSource(d.getSource())));
      LOGGER.info("listAllIndexDocuments([index]) : for index '{}' next 1000", index);
      searchResponse = elasticClient.prepareSearchScroll(searchResponse.getScrollId())
                                    .setScroll(TimeValue.timeValueHours(1))
                                    .execute()
                                    .actionGet();
    }
    while (searchResponse.getHits()
                         .getHits().length != 0);

    return indexedDocuments;

  }
}
