package com.github.onsdigital.index.enrichment.elastic;

/**
 * @author James Fawke
 */
public class InetAddressConfig {
  private String host;
  private Integer port;

  public String getHost() {
    return host;
  }

  public InetAddressConfig setHost(final String host) {
    this.host = host;
    return this;
  }

  public Integer getPort() {
    return port;
  }

  public InetAddressConfig setPort(final Integer port) {
    this.port = port;
    return this;
  }
}
