package com.github.onsdigital.index.enrichment.model.request;


import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class EnrichPageRequest implements PipelineRequest {

    private String fileContent;
    private String fileLocation;

    public String getFileLocation() {
        return fileLocation;
    }

    public EnrichPageRequest setFileLocation(final String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public String getFileContent() {
        return fileContent;
    }

    public EnrichPageRequest setFileContent(final String fileContent) {
        this.fileContent = fileContent;
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

        final EnrichPageRequest that = (EnrichPageRequest) o;

        return new EqualsBuilder()
                .append(getFileContent(), that.getFileContent())
                .append(getFileLocation(), that.getFileLocation())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getFileContent())
                .append(getFileLocation())
                .toHashCode();
    }
}