package com.github.onsdigital.index.enrichment.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James Fawke
 **/
@JsonIgnoreProperties(ignoreUnknown = true)
public class Document {

    @JsonProperty("_id")
    private String id;

    @JsonProperty("_index")
    private String index;

    @JsonProperty("_type")
    private String type;

    @JsonProperty("_version")
    private Long version;

    @JsonProperty("_score")
    private Float score;

    @JsonProperty("_source")
    private Map<String, Object> source;

    private boolean found;
    private String raw;

    public Float getScore() {
        return score;
    }

    public Document setScore(final Float score) {
        this.score = score;
        return this;
    }

    public boolean isFound() {
        return found;
    }

    public Document setFound(final boolean found) {
        this.found = found;
        return this;
    }

    public Long getVersion() {
        return version;
    }

    public Document setVersion(final Long version) {
        this.version = version;
        return this;
    }

    public Map<String, Object> getSource() {
        if (null == source) {
            source = new HashMap<>();
        }
        return source;
    }

    public Document setSource(final Map<String, Object> source) {
        this.source = source;
        return this;
    }

    public String getIndex() {
        return index;
    }

    public Document setIndex(final String index) {
        this.index = index;
        return this;
    }

    public String getType() {
        return type;
    }

    public Document setType(final String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public Document setId(final String id) {
        this.id = id;
        return this;
    }

    public String getRaw() {
        return raw;
    }

    public Document setRaw(final String raw) {
        this.raw = raw;
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

        final Document document = (Document) o;

        return new EqualsBuilder()
                .append(getId(), document.getId())
                .append(getIndex(), document.getIndex())
                .append(getType(), document.getType())
                .append(getSource(), document.getSource())
                .append(getRaw(), document.getRaw())
                .append(getVersion(), document.getVersion())
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
                .append(getVersion())
                .toHashCode();
    }
}
