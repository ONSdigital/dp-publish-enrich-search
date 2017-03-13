package com.github.onsdigital.index.enrichment.elastic;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.elastic.api.Action;
import com.github.onsdigital.index.enrichment.elastic.api.EndpointBuilder;
import com.github.onsdigital.index.enrichment.elastic.api.Request;
import com.github.onsdigital.index.enrichment.elastic.api.load.LoadBuilder;
import com.github.onsdigital.index.enrichment.elastic.api.search.model.SearchResults;
import com.github.onsdigital.index.enrichment.elastic.api.search.model.SearchResultsFactory;
import com.github.onsdigital.index.enrichment.elastic.api.update.UpdateBuilder;
import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import com.github.onsdigital.index.enrichment.exception.EnrichServiceException;
import com.github.onsdigital.index.enrichment.exception.ONSPageNotFoundException;
import com.github.onsdigital.index.enrichment.model.Document;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

import static com.github.onsdigital.index.enrichment.elastic.api.search.SearchBuilders.termQuery;

/**
 * Created by James Fawke on 01/02/2017.
 */
@Component
public class ElasticRepository {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticRepository.class);

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Map<String, String> emptyMap = Collections.emptyMap();

    @Autowired
    private RestClient elasticClient;


    RestClient getElasticClient() {
        return elasticClient;
    }

    ElasticRepository setElasticClient(final RestClient elasticClient) {
        this.elasticClient = elasticClient;
        return this;
    }

    public Document loadData(String id, String index, String type) throws ElasticSearchException {
        Document document = null;

        try {
            final Request request = new LoadBuilder().setElasticClient(getElasticClient())
                                                     .setIndex(index)
                                                     .setType(type)
                                                     .setId(id)
                                                     .build();

            Response response = request.execute();
            document = MAPPER.readValue(response.getEntity()
                                                .getContent(), Document.class);
        }
        catch (IOException io) {
            throw new ElasticSearchException("Failed to load Document ", io);
        }
        return document;
    }

    private String buildEndpoint(final String index, final String type, final String id, final Action action) {
        return new EndpointBuilder().setId(id)
                                    .setType(type)
                                    .setIndex(index)
                                    .setAction(action)
                                    .build();
    }

    public Document loadPage(String id, String index) throws EnrichServiceException, ElasticSearchException {


        Request termSearch = termQuery("_id", id).setSize(1)
                                                 .setIndex(index)
                                                 .setElasticClient(getElasticClient())
                                                 .build();


        final SearchResults searchResults;
        searchResults = performSearch(termSearch);


        Document returnPage;
        if (searchResults.getHits()
                         .getHits()
                         .size() == 1) {
            returnPage = searchResults.getHits()
                                      .getHits()
                                      .get(0);
        }
        else {
            throw new ONSPageNotFoundException("Could not find a page for '" + id + "' in index " + index);
        }

        return returnPage;

    }

    private SearchResults performSearch(Request termSearch) throws ElasticSearchException {
        final SearchResults searchResults;
        try {
            Response response = termSearch.execute();
            searchResults = SearchResultsFactory.getInstance(response);

        }
        catch (IOException e) {
            throw new ElasticSearchException("Failed to loadPage", e);
        }
        return searchResults;
    }


    public boolean upsertData(String id, String index, String type, Map<String, Object> updatedSource,
                              Long version) throws ElasticSearchException {
        final String s = buildEndpoint(index, type, id, Action.UPDATE);
        final Request request = new UpdateBuilder().setElasticClient(getElasticClient())
                                                 .setIndex(index)
                                                 .setType(type)
                                                 .setId(id)
                                                 .setDoc(updatedSource)
                                                 .build();
        final Response response = request.execute();
        final int statusCode = response.getStatusLine()
                                       .getStatusCode();
        return (statusCode == 200 || statusCode == 404);

    }

}
