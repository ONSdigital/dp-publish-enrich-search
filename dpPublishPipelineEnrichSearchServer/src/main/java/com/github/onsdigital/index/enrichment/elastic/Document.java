package com.github.onsdigital.index.enrichment.elastic;

import java.util.Map;

/**
 * Created by guidof on 08/03/17.
 */
public class Document {
    private String id;
    private String index;
    private String type;
    private Map<String, Object> source;
    private Long version;

    public String getId() {
        return id;
    }

    public void setId(final String id) {
        this.id = id;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(final String index) {
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public Map<String, Object> getSource() {
        return source;
    }

    public void setSource(final Map<String, Object> source) {
        this.source = source;
    }

    public Long getVersion() {
        return version;
    }

    public void setVersion(final Long version) {
        this.version = version;
    }
}
