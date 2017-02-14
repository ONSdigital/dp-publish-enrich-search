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
import org.elasticsearch.search.SearchHit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by James Fawke on 01/02/2017.
 */
@Component
public class ElasticRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticRepository.class);

    @Autowired
    private TransportClient elasticClient;


    TransportClient getElasticClient() {
        return elasticClient;
    }

    ElasticRepository setElasticClient(final TransportClient elasticClient) {
        this.elasticClient = elasticClient;
        return this;
    }

    public Data loadData(String id, String index, String type) {

        GetResponse document = getElasticClient().get(new GetRequest(index, type, id))
                                                 .actionGet();

        return new Data().setId(document.getId())
                         .setIndex(document.getIndex())
                         .setType(document.getType())
                         .setSource(document.getSource());

    }


    public void upsertData(String id, String index, String type, Map<String, Object> updatedSource) {
        UpdateRequestBuilder updateRequestBuilder = getElasticClient().prepareUpdate(index, type, id);

        updateRequestBuilder.setDoc(updatedSource);
        updateRequestBuilder.setUpsert(updatedSource);

        UpdateResponse updateResponse = updateRequestBuilder.execute()
                                                            .actionGet();


        LOGGER.info("upsertData([id => {}, index => {}, type => {}, was it new? ={}, version ={} ]) : ",
                    id,
                    index,
                    type,
                    updateResponse.isCreated(),
                    updateResponse.getVersion());

    }

    /**
     * Load all the documents for a reindex
     *
     * @param index
     * @return
     */
    public List<Data> listAllIndexDocuments(String index) {
        LOGGER.info("listAllIndexDocuments([index]) : for index '{}' ", index);


        SearchRequestBuilder query = getElasticClient().prepareSearch()
                                                       .setIndices(index)
                                                       .setScroll(TimeValue.timeValueHours(1))
                                                       .setSize(1000);

        LOGGER.info("listAllIndexDocuments([index]) : query '{}'", query);


        SearchResponse searchResponse = query.execute()
                                             .actionGet();

        final List<Data> indexedDocuments = new ArrayList<>();
        final AtomicLong i = new AtomicLong();
        SearchHit[] hits = searchResponse.getHits()
                                         .getHits();
        do {

            Arrays.stream(hits)
                  .forEach(d -> {
                      indexedDocuments.add(new Data().setId(d.getId())
                                                     .setIndex(d.getIndex())
                                                     .setType(d.getType())
                                                     .setSource(d.getSource()));
                  });

            LOGGER.info("listAllIndexDocuments([index]) : for  index '{}' next 1000", index);
            String scrollId = searchResponse.getScrollId();
            searchResponse = getElasticClient().prepareSearchScroll(scrollId)
                                               .setScroll(TimeValue.timeValueHours(1))
                                               .execute()
                                               .actionGet();
            hits = searchResponse.getHits()
                                 .getHits();
        }
        while (hits.length != 0);

        return indexedDocuments;

    }
}
