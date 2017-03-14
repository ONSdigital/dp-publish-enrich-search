package com.github.onsdigital.index.enrichment.elastic.api;

/**
 * Http Verbs used to define not only the HTTP request that will be issued but also the action on ElasticSearch
 */
public enum Verb {
    GET("GET"), PUT("PUT"), POST("POST"), DELETE("DELETE");

    private final String verb;

    Verb(final String verb) {
        this.verb = verb;
    }

    public String getVerb() {
        return verb;
    }
}