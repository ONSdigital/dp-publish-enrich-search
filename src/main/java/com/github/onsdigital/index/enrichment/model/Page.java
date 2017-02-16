package com.github.onsdigital.index.enrichment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James Fawke
 **/

public class Page {

    private String id;
    private String index;
    private String type;
    private Long version;
    private Map<String, Object> source;
    private String raw;
    private String dataFileLocation;

    public Long getVersion() {
        return version;
    }

    public Page setVersion(final Long version) {
        this.version = version;
        return this;
    }

    public Map<String, Object> getSource() {
        if (null == source) {
            source = new HashMap<>();
        }
        return source;
    }

    public Page setSource(final Map<String, Object> source) {
        this.source = source;
        return this;
    }

    public String getIndex() {
        return index;
    }

    public Page setIndex(final String index) {
        this.index = index;
        return this;
    }

    public String getType() {
        return type;
    }

    public Page setType(final String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public Page setId(final String id) {
        this.id = id;
        return this;
    }

    public String getRaw() {
        return raw;
    }

    public Page setRaw(final String raw) {
        this.raw = raw;
        return this;
    }

    public String getDataFileLocation() {
        return dataFileLocation;
    }

    public Page setDataFileLocation(final String dataFileLocation) {
        this.dataFileLocation = dataFileLocation;
        return this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final Page page = (Page) o;

        return new EqualsBuilder()
                .append(getId(), page.getId())
                .append(getIndex(), page.getIndex())
                .append(getType(), page.getType())
                .append(getSource(), page.getSource())
                .append(getRaw(), page.getRaw())
                .append(getDataFileLocation(), page.getDataFileLocation())
                .append(getVersion(), page.getVersion())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .append(getIndex())
                .append(getType())
                .append(getSource())
                .append(getRaw())
                .append(getDataFileLocation())
                .append(getVersion())
                .toHashCode();
    }
}
