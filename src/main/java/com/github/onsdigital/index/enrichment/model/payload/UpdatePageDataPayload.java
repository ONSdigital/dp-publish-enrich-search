package com.github.onsdigital.index.enrichment.model.payload;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

/**
 * Created by fawks on 15/02/2017.
 */
public class UpdatePageDataPayload extends AbstractPayload<UpdatePageDataPayload> {


    private List<String> content;

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UpdatePageDataPayload that = (UpdatePageDataPayload) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getContent(), that.getContent())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getContent())
                .toHashCode();
    }

    public List<String> getContent() {
        return content;
    }

    public UpdatePageDataPayload setContent(final List<String> content) {
        this.content = content;
        return this;
    }
}
