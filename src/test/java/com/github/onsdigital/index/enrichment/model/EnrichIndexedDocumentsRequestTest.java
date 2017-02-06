package com.github.onsdigital.index.enrichment.model;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fawks on 01/02/2017.
 */
public class EnrichIndexedDocumentsRequestTest {

  @Test
  public void testGetDocuments() throws Exception {
    EnrichDocument lhsDoc = new EnrichDocument().setId("abc")
                                                .setIndex("index123")
                                                .setType("Poiuuy");
    EnrichIndexedDocumentsRequest lhs = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(lhsDoc));

    assertEquals("abc",
                 lhs.getDocuments()
                    .get(0)
                    .getId());
    assertEquals("index123",
                 lhs.getDocuments()
                    .get(0)
                    .getIndex());
    assertEquals("Poiuuy",
                 lhs.getDocuments()
                    .get(0)
                    .getType());

  }

  @Test
  public void testEquals() throws Exception {
    EnrichDocument lhsDoc = new EnrichDocument().setId("abc")
                                                .setIndex("index123")
                                                .setType("Poiuuy");
    EnrichIndexedDocumentsRequest lhs = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(lhsDoc));
    EnrichDocument rhsDoc = new EnrichDocument().setId("abc")
                                                .setIndex("index123")
                                                .setType("Poiuuy");
    EnrichIndexedDocumentsRequest rhs = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(rhsDoc));
    assertEquals(lhs, rhs);
  }

  @Test
  public void testNotEquals() throws Exception {
    EnrichDocument lhsDoc = new EnrichDocument().setId("abc")
                                                .setIndex("index123")
                                                .setType("Poiuuy");
    EnrichIndexedDocumentsRequest lhs = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(lhsDoc));
    EnrichDocument rhsDoc = new EnrichDocument().setId("abc")
                                                .setIndex("Notindex123")
                                                .setType("Poiuuy");
    EnrichIndexedDocumentsRequest rhs = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(rhsDoc));
    assertNotEquals(lhs, rhs);
  }

  @Test
  public void testHashCode() throws Exception {
    EnrichDocument lhsDoc = new EnrichDocument().setId("abc")
                                                .setIndex("index123")
                                                .setType("Poiuuy");
    EnrichIndexedDocumentsRequest lhs = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(lhsDoc));
    EnrichDocument rhsDoc = new EnrichDocument().setId("abc")
                                                .setIndex("Notindex123")
                                                .setType("Poiuuy");
    EnrichIndexedDocumentsRequest rhs = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(rhsDoc));
    assertNotEquals(lhs.hashCode(), rhs.hashCode());

  }

  public static ObjectMapper MAPPER = new ObjectMapper();

  static {
    MAPPER.registerSubtypes(EnrichAllIndexedDocumentsRequest.class, EnrichIndexedDocumentsRequest.class);
  }

  private static final Logger LOGGER = LoggerFactory.getLogger(EnrichIndexedDocumentsRequestTest.class);

  @Test
  public void serialize() throws IOException {
    EnrichDocument enrichDocument = new EnrichDocument().setId("abc")
                                                        .setIndex("index123")
                                                        .setType("Poiuuy");
    EnrichIndexedDocumentsRequest expected = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(
        enrichDocument));
    String s = MAPPER.writeValueAsString(expected);
    LOGGER.info("serialize([]) : {}", s);
    Request actual = MAPPER.readValue(s, Request.class);
    assertEquals(expected, actual);

  }

}