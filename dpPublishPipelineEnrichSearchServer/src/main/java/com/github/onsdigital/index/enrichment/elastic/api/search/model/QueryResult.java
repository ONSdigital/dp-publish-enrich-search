package com.github.onsdigital.index.enrichment.elastic.api.search.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.onsdigital.index.enrichment.model.Page;

import java.util.List;

/**
 * Created by guidof on 08/03/17.
 */
public class QueryResult {

    private List<Page> hits;

    @JsonProperty("total")
    private Long totalHits;

    @JsonProperty("max_score")
    private Float maxScore;

    public Long getTotalHits() {
        return totalHits;
    }

    public QueryResult setTotalHits(final Long totalHits) {
        this.totalHits = totalHits;
        return this;
    }

    public Float getMaxScore() {
        return maxScore;
    }

    public QueryResult setMaxScore(final Float maxScore) {
        this.maxScore = maxScore;
        return this;
    }

    public List<Page> getHits() {
        return hits;
    }

    public QueryResult setHits(final List<Page> hits) {
        this.hits = hits;
        return this;
    }

}
