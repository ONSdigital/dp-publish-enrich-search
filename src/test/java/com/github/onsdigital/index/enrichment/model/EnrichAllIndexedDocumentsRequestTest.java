package com.github.onsdigital.index.enrichment.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fawks on 06/02/2017.
 */
public class EnrichAllIndexedDocumentsRequestTest {
  @Test
  public void getIndex() throws Exception {
    String testIndex = "testIndex";
    assertEquals(testIndex,
                 new EnrichAllIndexedDocumentsRequest().setIndex(testIndex)
                                                       .getIndex());
  }

  @Test
  public void testNotEqual() throws Exception {
    EnrichAllIndexedDocumentsRequest lhs = new EnrichAllIndexedDocumentsRequest().setIndex("testIndex1");
    EnrichAllIndexedDocumentsRequest rhs = new EnrichAllIndexedDocumentsRequest().setIndex("testIndex2");

    assertNotEquals(lhs, rhs);

  }


  @Test
  public void testEqual() throws Exception {
    EnrichAllIndexedDocumentsRequest lhs = new EnrichAllIndexedDocumentsRequest().setIndex("testIndex1");
    EnrichAllIndexedDocumentsRequest rhs = new EnrichAllIndexedDocumentsRequest().setIndex("testIndex1");

    assertEquals(lhs, rhs);

  }

  @Test
  public void testHashCode() throws Exception {
    EnrichAllIndexedDocumentsRequest lhs = new EnrichAllIndexedDocumentsRequest().setIndex("testIndex1");
    EnrichAllIndexedDocumentsRequest rhs = new EnrichAllIndexedDocumentsRequest().setIndex("testIndex1");

    assertEquals(lhs.hashCode(), rhs.hashCode());

  }
}