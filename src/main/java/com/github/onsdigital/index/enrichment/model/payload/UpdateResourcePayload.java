package com.github.onsdigital.index.enrichment.model.payload;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * Created by fawks on 15/02/2017.
 */
public class UpdateResourcePayload extends AbstractPayload<UpdateResourcePayload> {

    private Map<String, Object> content;
    private String s3Location;

    public String getS3Location() {
        return s3Location;
    }

    public UpdateResourcePayload setS3Location(final String s3Location) {
        this.s3Location = s3Location;
        return this;
    }

    public Map<String, Object> getContent() {
        return content;
    }

    public UpdateResourcePayload setContent(final Map<String, Object> content) {
        this.content = content;
        return (UpdateResourcePayload) this;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final UpdateResourcePayload that = (UpdateResourcePayload) o;

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
}
