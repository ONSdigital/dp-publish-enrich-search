package com.github.onsdigital.index.enrichment.aws;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test.
 */
public class AwsConfigurationTest {
  @Test
  public void getAccessKey() throws Exception {
    String testAccessKey = "testAccessKey";
    assertEquals(testAccessKey,
                 new AwsConfiguration().setAccessKey(testAccessKey)
                                       .getAccessKey());
  }

  @Test
  public void getSecretKey() throws Exception {
    String testSecretKey = "testSecretKey";
    assertEquals(testSecretKey,
                 new AwsConfiguration().setSecretKey(testSecretKey)
                                       .getSecretKey());

  }

}