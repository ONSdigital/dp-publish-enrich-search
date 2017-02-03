package com.github.onsdigital.index.enrichment.model;

/**
 * Created by fawks on 01/02/2017.
 */
public class EnrichAllIndexedDocumentsRequest implements Request {

  public String getIndex() {
    return index;
  }

  public void setIndex(final String index) {
    this.index = index;
  }

  String index;


}
