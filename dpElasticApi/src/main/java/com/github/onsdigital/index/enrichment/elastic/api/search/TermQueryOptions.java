package com.github.onsdigital.index.enrichment.elastic.api.search;

/**
 * Created by guidof on 08/03/17.
 */

public class TermQueryOptions {
    String value;
    Float boost;

    public String getValue() {
        return value;
    }

    public TermQueryOptions setValue(final String value) {
        this.value = value;
        return this;
    }

    public Float getBoost() {
        return boost;
    }

    public TermQueryOptions setBoost(final Float boost) {
        this.boost = boost;
        return this;
    }
}
