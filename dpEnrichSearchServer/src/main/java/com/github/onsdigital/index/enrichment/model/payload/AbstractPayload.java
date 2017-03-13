package com.github.onsdigital.index.enrichment.model.payload;

import com.github.onsdigital.index.enrichment.model.Document;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Abstract Parent Template that holds the common fields betweent the different payloads.
 */
public abstract class AbstractPayload<T> {
    private Document document;
    private String uri;

    public Document getDocument() {
        return document;
    }


    public T setPage(final Document document) {

        this.document = document;
        return (T) this;
    }


    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final AbstractPayload<?> that = (AbstractPayload<?>) o;

        return new EqualsBuilder()
                .append(getDocument(), that.getDocument())
                             .append(getUri(), that.getUri())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getDocument())
                .append(getUri())
                .toHashCode();
    }

    public String getUri() {
        return uri;
    }

    public T setUri(final String uri) {
        this.uri = uri;
        return (T) this;
    }
}
