package com.github.onsdigital.index.enrichment.model;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.model.transformer.SubTypeMapper;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by fawks on 06/02/2017.
 */
public class RequestSerializationTest {
  SubTypeMapper<Request> requestMapper = new SubTypeMapper<>(Request.class);
  ObjectMapper genericMapper = new ObjectMapper();

  @Test
  public void testEnrichAllIndexedDocumentsRequest() throws IOException {
    EnrichAllIndexedDocumentsRequest e = new EnrichAllIndexedDocumentsRequest().setIndex("testIndex");
    Request a = requestMapper.readValue(genericMapper.writeValueAsBytes(e));
    assertEquals(e, a);
  }

  @Test
  public void testEnrichIndexedDocumentsRequest() throws IOException {

    EnrichDocument enrichDocument = new EnrichDocument().setId("testId")
                                                        .setIndex("testIndex")
                                                        .setType("testType");
    EnrichIndexedDocumentsRequest e = new EnrichIndexedDocumentsRequest().setDocuments(Lists.newArrayList(enrichDocument));

    Request a = requestMapper.readValue(genericMapper.writeValueAsBytes(e));
    assertEquals(e, a);

  }


  @Test
  public void testEnrichResourceDocumentsRequest() throws IOException {
    List<ResourceDocument> res = Lists.newArrayList(new ResourceDocument().setDataFileLocation("testFileLocation"));
    EnrichResourceDocumentsRequest e = new EnrichResourceDocumentsRequest().setResources(res);
    Request a = requestMapper.readValue(genericMapper.writeValueAsBytes(e));
    assertEquals(e, a);

  }


}