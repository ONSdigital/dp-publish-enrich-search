package com.github.onsdigital.index.enrichment.elastic;

import com.beust.jcommander.internal.Lists;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by fawks on 07/02/2017.
 */
public class ElasticConfigTest {

  @Test
  public void isIgnoreClusterName() throws Exception {
    ElasticConfig config = new ElasticConfig();
    assertFalse(config.setIgnoreClusterName(false)
                      .isIgnoreClusterName());
    assertTrue(config.setIgnoreClusterName(true)
                     .isIgnoreClusterName());
  }

  @Test
  public void getInetAddresses() throws Exception {
    ElasticConfig config = new ElasticConfig();
    String testHostName = "testHost";
    Integer testHostPort = -1000;
    List<InetAddressConfig> testHost = Lists.newArrayList(new InetAddressConfig().setPort(testHostPort)
                                                                                 .setHost(testHostName));
    ElasticConfig elasticConfig = config.setInetAddresses(testHost);
    assertEquals(testHostPort,
                 elasticConfig.getInetAddresses()
                              .get(0)
                              .getPort());
    assertEquals(testHostName,
                 elasticConfig.getInetAddresses()
                              .get(0)
                              .getHost());
  }

  @Test
  public void getClusterName() throws Exception {
    ElasticConfig config = new ElasticConfig();
    String testClusterName = "testClusterName";
    assertEquals(testClusterName,
                 config.setClusterName(testClusterName)
                       .getClusterName());

  }
  @Test
  public void testHoshCode() {

    ElasticConfig lhs = buildConfig("testClusterName", true, "testHostName", -11111);
    ElasticConfig rhs = buildConfig("testClusterName", true, "testHostName", -11111);
    assertEquals(lhs.hashCode(), rhs.hashCode());
  }
  @Test
  public void testEquals() {

    ElasticConfig lhs = buildConfig("testClusterName", true, "testHostName", -11111);
    ElasticConfig rhs = buildConfig("testClusterName", true, "testHostName", -11111);
    assertEquals(lhs, rhs);
  }

  @Test
  public void testNotEqualsUneqHostName() {

    ElasticConfig lhs = buildConfig("testClusterName", true, "testHostName1", -11111);
    ElasticConfig rhs = buildConfig("testClusterName", true, "testHostName2", -11111);
    assertNotEquals(lhs, rhs);
  }

  @Test
  public void testNotEqualsUneqIgnoreClustername() {

    ElasticConfig lhs = buildConfig("testClusterName", true, "testHostName", -11111);
    ElasticConfig rhs = buildConfig("testClusterName", false, "testHostName", -11111);
    assertNotEquals(lhs, rhs);
  }

  @Test
  public void testNotEqualsUneqClustername() {

    ElasticConfig lhs = buildConfig("testClusterName1", true, "testHostName", -11111);
    ElasticConfig rhs = buildConfig("testClusterName2", true, "testHostName", -11111);
    assertNotEquals(lhs, rhs);
  }

  private ElasticConfig buildConfig(final String testClusterName, final Boolean ignoreClusterName,
                                    final String testHostName, final Integer testHostPort) {


    return ConfigUtils.buildConfig(testClusterName, ignoreClusterName, testHostName, testHostPort);
  }

}