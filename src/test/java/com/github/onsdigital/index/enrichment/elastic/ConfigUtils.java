package com.github.onsdigital.index.enrichment.elastic;

import com.google.common.collect.Lists;

import java.util.List;

public class ConfigUtils {
  private ConfigUtils() {
    //DO NOT INSTANTIATE
  }

  static ElasticConfig buildConfig(final String testClusterName, final Boolean ignoreClusterName,
                                   final String testHostName, final Integer testHostPort) {


    List<InetAddressConfig> testHost = Lists.newArrayList(new InetAddressConfig().setPort(testHostPort)
                                                                                 .setHost(testHostName));
    return new ElasticConfig()
        .setClusterName(testClusterName)
        .setIgnoreClusterName(ignoreClusterName)
        .setInetAddresses(testHost);
  }
}