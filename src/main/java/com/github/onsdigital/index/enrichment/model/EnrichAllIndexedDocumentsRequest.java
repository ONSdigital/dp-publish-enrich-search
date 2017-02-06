package com.github.onsdigital.index.enrichment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * Created by fawks on 01/02/2017.
 */
public class EnrichAllIndexedDocumentsRequest implements Request {

  String index;

  public String getIndex() {
    return index;
  }

  public EnrichAllIndexedDocumentsRequest setIndex(final String index) {
    this.index = index;
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
    EnrichAllIndexedDocumentsRequest rhs = (EnrichAllIndexedDocumentsRequest) o;
    EnrichAllIndexedDocumentsRequest lhs = this;

    return new EqualsBuilder().append(lhs.getIndex(), rhs.getIndex())
                              .isEquals();


  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder().append(this.getIndex())
                                .toHashCode();
  }
}
