package com.github.onsdigital.index.enrichment.elastic.api.search.model;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.elasticsearch.client.Response;

import java.io.IOException;

/**
 * Created by guidof on 08/03/17.
 */
public class SearchResultsFactory {
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                                                                            false);

    private SearchResultsFactory() {
        //DO NOT INSTANTIATE
    }

    public static SearchResults getInstance(Response searchResponse) throws IOException {
        return MAPPER.readValue(searchResponse.getEntity()
                                              .getContent(), SearchResults.class);
    }
}
