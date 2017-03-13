package com.github.onsdigital.index.enrichment.elastic.api.search;

/**
 * Created by guidof on 08/03/17.
 */
public class SearchBuilders {
    private SearchBuilders() {
        //DO NOT INSTANTIATE
    }

    public static final SearchBuilder termQuery(final String field, final String value) {
        return new SearchBuilder().setQuery(new TermQuery(field, new TermQueryOptions().setValue(value)));
    }

}
