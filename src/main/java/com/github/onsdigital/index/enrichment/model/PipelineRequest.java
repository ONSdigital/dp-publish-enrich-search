package com.github.onsdigital.index.enrichment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by fawks on 10/02/2017.
 */
public class PipelineRequest {
    private String collectionId;
    private String fileLocation;
    private String s3Location;

    public String getCollectionId() {
        return collectionId;
    }

    public PipelineRequest setCollectionId(final String collectionId) {
        this.collectionId = collectionId;
        return this;
    }

    public String getFileLocation() {
        return fileLocation;
    }

    public PipelineRequest setFileLocation(final String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public String getS3Location() {
        return s3Location;
    }

    public PipelineRequest setS3Location(final String s3Location) {
        this.s3Location = s3Location;
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

        final PipelineRequest that = (PipelineRequest) o;

        return new EqualsBuilder()
                .append(getCollectionId(), that.getCollectionId())
                .append(getFileLocation(), that.getFileLocation())
                .append(getS3Location(), that.getS3Location())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getCollectionId())
                .append(getFileLocation())
                .append(getS3Location())
                .toHashCode();
    }
}
