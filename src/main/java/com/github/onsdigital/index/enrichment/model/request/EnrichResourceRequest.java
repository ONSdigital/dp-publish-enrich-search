package com.github.onsdigital.index.enrichment.model.request;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Request the enrichment of a document based on a file resource
 */
public class EnrichResourceRequest implements PipelineRequest {


    private String s3Location;
    private String uri;


    public String getS3Location() {
        return s3Location;
    }

    public EnrichResourceRequest setS3Location(final String s3Location) {
        this.s3Location = s3Location;
        return this;
    }

    public String getUri() {
        return uri;
    }

    public EnrichResourceRequest setUri(final String uri) {
        this.uri = uri;
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

        final EnrichResourceRequest that = (EnrichResourceRequest) o;

        return new EqualsBuilder()
                .append(getS3Location(), that.getS3Location())
                .append(getUri(), that.getUri())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getS3Location())
                .append(getUri())
                .toHashCode();
    }

}