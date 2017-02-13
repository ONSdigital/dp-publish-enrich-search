package com.github.onsdigital.index.enrichment.model;

/**
 * Created by fawks on 10/02/2017.
 */
public class PipelineRequest implements Request {
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

    private String collectionId;
    private String fileLocation;
    private String s3Location;
}
