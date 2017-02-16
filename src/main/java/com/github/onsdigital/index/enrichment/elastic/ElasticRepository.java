package com.github.onsdigital.index.enrichment.elastic;

import com.github.onsdigital.index.enrichment.exception.InValidUpdateRequestException;
import com.github.onsdigital.index.enrichment.exception.ONSPageNotFoundException;
import com.github.onsdigital.index.enrichment.model.Page;
import org.elasticsearch.action.ActionRequestValidationException;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequestBuilder;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
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

    public Page loadData(String id, String index, String type) {

        GetResponse document = getElasticClient().get(new GetRequest(index, type, id))
                                                 .actionGet();

        return new Page().setId(document.getId())
                         .setIndex(document.getIndex())
                         .setType(document.getType())
                         .setSource(document.getSource())
                         .setVersion(document.getVersion());

    }

    public Page loadPage(String id, String index) throws ONSPageNotFoundException {
        Page returnPage = null;
        SearchHits hits = getElasticClient().prepareSearch()
                                            .setIndices(index)
                                            .setQuery(QueryBuilders.termQuery("_id", id))
                                            .setSize(1)
                                            .setVersion(true)
                                            .execute()
                                            .actionGet()
                                            .getHits();

        if (hits.getHits().length == 1) {
            SearchHit page = hits.getHits()[0];
            returnPage = new Page().setId(page.getId())
                                   .setIndex(page.getIndex())
                                   .setType(page.getType())
                                   .setSource(page.getSource())
                                   .setVersion(page.getVersion());
        }
        else {
            throw new ONSPageNotFoundException("Could not find a page for '" + id + "' in index " + index);
        }

        return returnPage;

    }


    public void upsertData(String id, String index, String type, Map<String, Object> updatedSource,
                           Long version) throws InValidUpdateRequestException {
        UpdateRequestBuilder updateRequestBuilder = getElasticClient().prepareUpdate(index, type, id);
        //If Version sent use it
        if (null != version) {
            updateRequestBuilder.setVersion(version);
        }
        updateRequestBuilder.setDoc(updatedSource);
        updateRequestBuilder.setUpsert(updatedSource);
        try {

            UpdateResponse updateResponse = updateRequestBuilder.execute()
                                                                .actionGet();
            LOGGER.info("upsertData([id => {}, index => {}, type => {}, was it new? ={}, version ={} ]) : ",
                        id,
                        index,
                        type,
                        updateResponse.isCreated(),
                        updateResponse.getVersion());
        }
        catch (ActionRequestValidationException re) {
            LOGGER.warn(
                    "upsertData([id, index, type, updatedSource, version]) : failed to update document '{}' due to {}",
                    id,
                    re.getMessage());
            throw new InValidUpdateRequestException("Error inserting record " + re.getMessage(), re);
        }



    }

    /**
     * Load all the documents for a reindex
     *
     * @param index
     * @return
     */
    public List<Page> listAllIndexDocuments(String index) {
        LOGGER.info("listAllIndexDocuments([index]) : for index '{}' ", index);


        SearchRequestBuilder query = getElasticClient().prepareSearch()
                                                       .setIndices(index)
                                                       .setScroll(TimeValue.timeValueHours(1))
                                                       .setSize(1000);

        LOGGER.info("listAllIndexDocuments([index]) : query '{}'", query);


        SearchResponse searchResponse = query.execute()
                                             .actionGet();

        final List<Page> indexedDocuments = new ArrayList<>();
        final AtomicLong i = new AtomicLong();
        SearchHit[] hits = searchResponse.getHits()
                                         .getHits();
        do {

            Arrays.stream(hits)
                  .forEach(d -> {
                      indexedDocuments.add(new Page().setId(d.getId())
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
