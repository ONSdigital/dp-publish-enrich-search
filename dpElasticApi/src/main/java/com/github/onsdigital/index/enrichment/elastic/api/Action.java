package com.github.onsdigital.index.enrichment.elastic.api;

/**
 * Created by guidof on 08/03/17.
 */
public enum Action {

    SEARCH("_search"),
    UPDATE("_update");

    private final String action;

    Action(final String action) {
        this.action = action;
    }

    public String getAction() {
        return action;
    }
}
