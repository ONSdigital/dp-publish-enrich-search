package com.github.onsdigital.index.enrichment.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test.
 */
public class ModelEnumTest {
  @Test
  public void getIndexDocProperty() throws Exception {
    assertEquals("_id", ModelEnum.ID.getIndexDocProperty());
  }

  @Test
  public void getFileProperty() throws Exception {
    assertEquals("uri", ModelEnum.ID.getFileProperty());

  }

}