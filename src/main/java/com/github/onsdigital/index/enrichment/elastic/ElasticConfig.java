package com.github.onsdigital.index.enrichment.elastic;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author James Fawke
 */
@ConfigurationProperties(prefix = "elasticsearch")
@Component
public class ElasticConfig {

  private List<InetAddressConfig> inetAddresses;
  private String clusterName;
  private Boolean ignoreClusterName;

  public Boolean isIgnoreClusterName() {
    return ignoreClusterName;
  }

  public ElasticConfig setIgnoreClusterName(final Boolean ignoreClusterName) {
    this.ignoreClusterName = ignoreClusterName;
    return this;
  }

  public List<InetAddressConfig> getInetAddresses() {
    return inetAddresses;
  }

  public void setInetAddresses(final List<InetAddressConfig> inetAddresses) {
    this.inetAddresses = inetAddresses;
  }

  public String getClusterName() {
    return clusterName;
  }

  public ElasticConfig setClusterName(final String clusterName) {
    this.clusterName = clusterName;
    return this;

  }
}
