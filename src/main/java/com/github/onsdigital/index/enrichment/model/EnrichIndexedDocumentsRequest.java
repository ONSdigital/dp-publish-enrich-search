package com.github.onsdigital.index.enrichment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Allows for a specific resources to be enriched
 */
public class EnrichIndexedDocumentsRequest implements Request {

    List<EnrichDocument> documents;

    public List<EnrichDocument> getDocuments() {
        return documents;
    }

    public EnrichIndexedDocumentsRequest setDocuments(final List<EnrichDocument> documents) {
        this.documents = documents;
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

        final EnrichIndexedDocumentsRequest rhs = (EnrichIndexedDocumentsRequest) o;
        final EnrichIndexedDocumentsRequest lhs = this;

        return new EqualsBuilder().append(lhs.documents, rhs.documents)
                                  .isEquals();

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(documents)
                                    .toHashCode();
    }
}
