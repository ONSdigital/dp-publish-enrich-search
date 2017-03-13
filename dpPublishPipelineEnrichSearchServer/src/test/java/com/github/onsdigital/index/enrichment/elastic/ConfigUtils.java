package com.github.onsdigital.index.enrichment.elastic;

import com.google.common.collect.Lists;

import java.util.List;

public class ConfigUtils {
  private ConfigUtils() {
    //DO NOT INSTANTIATE
  }

  static ElasticConfig buildConfig(final String testHostName, final Integer testHostPort) {


    List<HttpAddressConfig> testHost = Lists.newArrayList(new HttpAddressConfig().setPort(testHostPort)
                                                                                 .setHost(testHostName));
    return new ElasticConfig()
        .setHttpAddresses(testHost);
  }
}