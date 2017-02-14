package com.github.onsdigital.index.enrichment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Allows for a specific resources to be enriched based on there file system (inc AWS S3) URL
 */
public class EnrichResourceDocumentsRequest implements Request {

    List<ResourceDocument> resources;

    public List<ResourceDocument> getResources() {
        return resources;
    }

    public EnrichResourceDocumentsRequest setResources(final List<ResourceDocument> resources) {
        this.resources = resources;
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

        final EnrichResourceDocumentsRequest rhs = (EnrichResourceDocumentsRequest) o;
        final EnrichResourceDocumentsRequest lhs = this;

        return new EqualsBuilder().append(lhs.resources, rhs.resources)
                                  .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(resources)
                                    .toHashCode();
    }
}
