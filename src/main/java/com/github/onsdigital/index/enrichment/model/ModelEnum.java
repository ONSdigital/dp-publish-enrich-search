package com.github.onsdigital.index.enrichment.model;

/**
 * Constants class
 */
public enum ModelEnum {
    DOWNLOADS("downloads", "downloads"),
    FILE("file", "file"),
    CONTENT("", "content"),
    URI("uri", "uri"),
    ID("uri", "_id"),
    INDEX("", "_index"),
    TYPE("type", "_type"),
    PAGEDATA("", "pageData");

    private String fileProperty;
    private String indexDocProperty;

    ModelEnum(final String fileProperty, final String indexDocProperty) {

        this.fileProperty = fileProperty;
        this.indexDocProperty = indexDocProperty;
    }

    public String getIndexDocProperty() {
        return indexDocProperty;
    }

    public String getFileProperty() {
        return fileProperty;
    }


}
