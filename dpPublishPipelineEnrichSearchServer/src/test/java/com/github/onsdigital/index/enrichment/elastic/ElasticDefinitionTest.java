package com.github.onsdigital.index.enrichment.elastic;

/**
 * Created by fawks on 07/02/2017.
 */
public class ElasticDefinitionTest {
  //TODO
//
//  @Test
//  public void elasticClient() throws Exception {
//    Boolean ignoreClusterName = true;
//    String testClusterName = "testClusterName";
//    ElasticConfig config = buildConfig(testClusterName, ignoreClusterName, "0.0.0.0", 9999);
//    TransportClient transportClient = new ElasticDefinition().elasticClient(config);
//    assertNotNull(transportClient);
//    assertEquals(ignoreClusterName,
//                 Boolean.parseBoolean(transportClient.settings()
//                                                     .get("client.transport.ignore_cluster_name")));
//    assertEquals(testClusterName,
//                 transportClient.settings()
//                                .get("cluster.name"));
//    assertEquals(1,
//                 transportClient.transportAddresses()
//                                .size());
//    assertEquals(9999,
//                 transportClient.transportAddresses()
//                                .get(0)
//                                .getPort());
//    assertEquals("0.0.0.0",
//                 transportClient.transportAddresses()
//                                .get(0)
//                                .getHost());
//
//  }

}