package com.github.onsdigital.index.enrichment.model.transformer;

import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.model.EnrichResourceDocumentsRequest;
import com.github.onsdigital.index.enrichment.model.Request;
import com.github.onsdigital.index.enrichment.model.ResourceDocument;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Created by fawks on 06/02/2017.
 */
public class RequestTransformerTest {
  @Test
  public void testRequestTransform() throws JsonProcessingException {
    String testLocation = "TestLocation";
    List<ResourceDocument> expectedResources = Lists.newArrayList(new ResourceDocument().setDataFileLocation(
        testLocation));
    EnrichResourceDocumentsRequest expected = new EnrichResourceDocumentsRequest().setResources(expectedResources);
    ObjectMapper mapper = new ObjectMapper();
    byte[] expectedBytes = mapper.writeValueAsBytes(expected);
    Request actual = new RequestTransformer().transform(expectedBytes);
    assertEquals(expected, actual);

  }



}