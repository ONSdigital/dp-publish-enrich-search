package com.github.onsdigital.index.enrichment.elastic.api;

/**
 * Current known fields that will are understood my default by the current builders, other options are available
 * but you need to know where to put them in the document or in the URL parameters
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
