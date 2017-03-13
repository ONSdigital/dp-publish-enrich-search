package com.github.onsdigital.index.enrichment.elastic.api;

/**
 * Created by guidof on 09/03/17.
 */
public enum ElasticParameters {
    STORED_FIELDS("stored_fields"),
    SIZE("size"),
    FROM("from"),
    DOC("doc"),
    UPSERT("upsert"),
    VERSION("version"),
    QUERY("query");

    private final String text;

    ElasticParameters(final String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}
