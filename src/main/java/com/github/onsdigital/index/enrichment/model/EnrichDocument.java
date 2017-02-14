package com.github.onsdigital.index.enrichment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Defined the parameters for the request
 */
public class EnrichDocument {

    String id;
    String index;
    String type;

    public String getId() {
        return id;
    }

    public EnrichDocument setId(final String id) {
        this.id = id;
        return this;
    }

    public String getIndex() {
        return index;
    }

    public EnrichDocument setIndex(final String index) {
        this.index = index;
        return this;
    }

    public String getType() {
        return type;
    }

    public EnrichDocument setType(final String type) {
        this.type = type;
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
        EnrichDocument rhs = (EnrichDocument) o;
        EnrichDocument lhs = this;
        return new EqualsBuilder().append(lhs.getId(), rhs.getId())
                                  .append(lhs.getIndex(), rhs.getIndex())
                                  .append(lhs.getType(), rhs.getType())
                                  .isEquals();


    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(this.getId())
                                    .append(this.getIndex())
                                    .append(this.getType())
                                    .toHashCode();

    }
}
