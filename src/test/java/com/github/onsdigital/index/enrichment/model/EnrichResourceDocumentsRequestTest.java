package com.github.onsdigital.index.enrichment.model;

import com.beust.jcommander.internal.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fawks on 06/02/2017.
 */
public class EnrichResourceDocumentsRequestTest {
  @Test
  public void getResources() throws Exception {
    ResourceDocument exp = new ResourceDocument().setDataFileLocation("testLocations");
    List<ResourceDocument> actuals = new EnrichResourceDocumentsRequest().setResources(Lists.newArrayList(exp))
                                                                         .getResources();

    assertEquals(1, actuals.size());
    assertEquals(exp, actuals.get(0));

  }

  @Test
  public void testNotEqual() throws Exception {
    ResourceDocument lhsRes = new ResourceDocument().setDataFileLocation("testLocations1");
    EnrichResourceDocumentsRequest lhs = new EnrichResourceDocumentsRequest().setResources(Lists.newArrayList(lhsRes));
    ResourceDocument rhsRes = new ResourceDocument().setDataFileLocation("testLocations2");
    EnrichResourceDocumentsRequest rhs = new EnrichResourceDocumentsRequest().setResources(Lists.newArrayList(rhsRes));
    assertNotEquals(lhs, rhs);

  }


  @Test
  public void testEqual() throws Exception {
    ResourceDocument lhsRes = new ResourceDocument().setDataFileLocation("testLocations1");
    EnrichResourceDocumentsRequest lhs = new EnrichResourceDocumentsRequest().setResources(Lists.newArrayList(lhsRes));
    ResourceDocument rhsRes = new ResourceDocument().setDataFileLocation("testLocations1");
    EnrichResourceDocumentsRequest rhs = new EnrichResourceDocumentsRequest().setResources(Lists.newArrayList(rhsRes));
    assertEquals(lhs, rhs);

  }

  @Test
  public void testHashCode() throws Exception {
    ResourceDocument lhsRes = new ResourceDocument().setDataFileLocation("testLocations1");
    EnrichResourceDocumentsRequest lhs = new EnrichResourceDocumentsRequest().setResources(Lists.newArrayList(lhsRes));
    ResourceDocument rhsRes = new ResourceDocument().setDataFileLocation("testLocations1");
    EnrichResourceDocumentsRequest rhs = new EnrichResourceDocumentsRequest().setResources(Lists.newArrayList(rhsRes));
    assertEquals(lhs.hashCode(), rhs.hashCode());

  }

}