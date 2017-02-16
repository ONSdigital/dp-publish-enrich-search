package com.github.onsdigital.index.enrichment.model.payload;

import com.github.onsdigital.index.enrichment.model.Page;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by fawks on 15/02/2017.
 */
public abstract class AbstractPayload<T> {
    private Page page;
    private String uri;

    public Page getPage() {
        return page;
    }


    public T setPage(final Page page) {

        this.page = page;
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
                .append(getPage(), that.getPage())
                             .append(getUri(), that.getUri())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getPage())
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
