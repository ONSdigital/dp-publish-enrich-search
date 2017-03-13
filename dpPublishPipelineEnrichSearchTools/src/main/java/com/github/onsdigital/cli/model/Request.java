package com.github.onsdigital.cli.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by guidof on 10/03/17.
 */
public class Request {

    private String fileContent;
    private String fileLocation;

    public String getFileLocation() {
        return fileLocation;
    }

    public Request setFileLocation(final String fileLocation) {
        this.fileLocation = fileLocation;
        return this;
    }

    public String getFileContent() {
        return fileContent;
    }

    public Request setFileContent(final String fileContent) {
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

        final Request that = (Request) o;

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