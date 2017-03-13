package com.github.onsdigital.index.enrichment.model;

/**
 * Constants class
 */
public enum ModelEnum {
    DOWNLOADS("downloads", "downloads"),
    FILE("file", "file"),
    CONTENT("", "content"),
    ID("uri", "_id"),
    PAGEDATA(null, "pageData"),
    UPDATED_AT(null, "updatedAt");

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
