package com.github.onsdigital.index.enrichment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * This should handle S3, http and localfile system if defined correctly i.e. s3://mybucket/file.
 */
public class ResourceDocument {

  private String dataFileLocation;

  @Override
  public boolean equals(final Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    final ResourceDocument rhs = (ResourceDocument) o;
    final ResourceDocument lhs = this;
    return new EqualsBuilder().append(lhs.getDataFileLocation(), rhs.getDataFileLocation())
                              .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(getDataFileLocation())
                                .toHashCode();
  }

  public String getDataFileLocation() {
    return dataFileLocation;
  }

  public void setDataFileLocation(final String dataFileLocation) {
    this.dataFileLocation = dataFileLocation;
  }
}
