package com.github.onsdigital.index.enrichment.model;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.Map;

/**
 * @author James Fawke
 **/

public class Data {

  private String id;
  private String index;
  private String type;

  private Map<String, Object> source;
  private String raw;
  private String dataFileLocation;

  public Map<String, Object> getSource() {
    return source;
  }

  public Data setSource(final Map<String, Object> source) {
    this.source = source;
    return this;
  }

  public String getIndex() {
    return index;
  }

  public Data setIndex(final String index) {
    this.index = index;
    return this;
  }

  public String getType() {
    return type;
  }

  public Data setType(final String type) {
    this.type = type;
    return this;
  }

  public String getId() {
    return id;
  }

  public Data setId(final String id) {
    this.id = id;
    return this;
  }

  public String getRaw() {
    return raw;
  }

  public Data setRaw(final String raw) {
    this.raw = raw;
    return this;
  }

  public String getDataFileLocation() {
    return dataFileLocation;
  }

  public Data setDataFileLocation(final String dataFileLocation) {
    this.dataFileLocation = dataFileLocation;
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

    final Data data = (Data) o;

    return new EqualsBuilder()
        .append(getId(), data.getId())
        .append(getIndex(), data.getIndex())
        .append(getType(), data.getType())
        .append(getSource(), data.getSource())
        .append(getRaw(), data.getRaw())
        .append(getDataFileLocation(), data.getDataFileLocation())
        .isEquals();
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder(17, 37)
        .append(getId())
        .append(getIndex())
        .append(getType())
        .append(getSource())
        .append(getRaw())
        .append(getDataFileLocation())
        .toHashCode();
  }
}
