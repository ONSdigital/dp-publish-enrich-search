package com.github.onsdigital.index.enrichment.elastic.api.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object that represents the results from the search engine
 */
public class SearchResults {

    @JsonProperty("took")
    private Long queryTookMs;

    @JsonProperty("timed_out")
    private Boolean timedOut;
    private QueryResult hits;

    public Long getQueryTookMs() {
        return queryTookMs;
    }

    public SearchResults setQueryTookMs(final Long queryTookMs) {
        this.queryTookMs = queryTookMs;
        return this;
    }

    public Boolean getTimedOut() {
        return timedOut;
    }

    public SearchResults setTimedOut(final Boolean timedOut) {
        this.timedOut = timedOut;
        return this;
    }

    public QueryResult getHits() {
        return hits;
    }

    public SearchResults setHits(final QueryResult hits) {
        this.hits = hits;
        return this;
    }
}
