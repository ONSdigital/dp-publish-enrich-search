package com.github.onsdigital.index.enrichment.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author James Fawke
 */
@ConfigurationProperties(prefix = "kafka")
@Component
public class KafkaConfig {

  private String topic;
  private String messageKey;
  private Boolean autoStart;
  private ZookeeperConfig zookeeper = new ZookeeperConfig();

  public String getTopic() {
    return topic;
  }

  public void setTopic(final String topic) {
    this.topic = topic;
  }

  public String getMessageKey() {
    return messageKey;
  }

  public void setMessageKey(final String messageKey) {
    this.messageKey = messageKey;
  }

  public Boolean getAutoStart() {
    return autoStart;
  }

  public void setAutoStart(final Boolean autoStart) {
    this.autoStart = autoStart;
  }

  public ZookeeperConfig getZookeeper() {
    return zookeeper;
  }

  public void setZookeeper(final ZookeeperConfig zookeeper) {
    this.zookeeper = zookeeper;
  }

  public static class ZookeeperConfig {
    private String connect;

    public String getConnect() {
      return connect;
    }

    public void setConnect(final String connect) {
      this.connect = connect;
    }
  }
}