package com.github.onsdigital.index.enrichment.elastic.api;

/**
 * Created by guidof on 09/03/17.
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