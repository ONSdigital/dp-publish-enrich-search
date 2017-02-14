package com.github.onsdigital.index.enrichment.kafka;

import org.apache.commons.lang3.StringUtils;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author James Fawke
 */
@ConfigurationProperties(prefix = "spring.kafka.consumer")
@Component
public class KafkaConsumerConfig {
  @Autowired
  KafkaConfig kafkaCommonConfig;
  private String groupId;
  private Boolean enableAutoCommit;
  private Integer autoCommitInterval;
  private String valueDeserializer;
  private String keyDeserializer;
  private Integer maxPollRecords;
  private Integer maxPollInterval;

  public Integer getMaxPollRecords() {
    return maxPollRecords;
  }

  public KafkaConsumerConfig setMaxPollRecords(final Integer maxPollRecords) {
    this.maxPollRecords = maxPollRecords;
    return this;
  }

  public Integer getMaxPollInterval() {
    return maxPollInterval;
  }

  public KafkaConsumerConfig setMaxPollInterval(final Integer maxPollInterval) {
    this.maxPollInterval = maxPollInterval;
    return this;
  }

  public KafkaConfig getKafkaCommonConfig() {
    return kafkaCommonConfig;
  }

  public void setKafkaCommonConfig(final KafkaConfig kafkaCommonConfig) {
    this.kafkaCommonConfig = kafkaCommonConfig;
  }

  public String getGroupId() {
    return groupId;
  }

  public void setGroupId(final String groupId) {
    this.groupId = groupId;
  }

  public Boolean getEnableAutoCommit() {
    return enableAutoCommit;
  }

  public void setEnableAutoCommit(final Boolean enableAutoCommit) {
    this.enableAutoCommit = enableAutoCommit;
  }

  public Integer getAutoCommitInterval() {
    return autoCommitInterval;
  }

  public void setAutoCommitInterval(final Integer autoCommitInterval) {
    this.autoCommitInterval = autoCommitInterval;
  }

  public String getValueDeserializer() {
    return valueDeserializer;
  }

  public void setValueDeserializer(final String valueDeserializer) {
    this.valueDeserializer = valueDeserializer;
  }

  public String getKeyDeserializer() {
    return keyDeserializer;
  }

  public void setKeyDeserializer(final String keyDeserializer) {
    this.keyDeserializer = keyDeserializer;
  }

  public Map<String, Object> getConfig() {
    Map<String, Object> config = new HashMap<>();

    if (null != kafkaCommonConfig
        && StringUtils.isNotBlank(kafkaCommonConfig.getZookeeper()
                                                   .getConnect())) {

      config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
                 kafkaCommonConfig.getZookeeper()
                                  .getConnect());
    }

    if (StringUtils.isNotBlank(groupId)) {
      config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    }

    if (StringUtils.isNotBlank(valueDeserializer)) {
      config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializer);
    }

    if (StringUtils.isNotBlank(keyDeserializer)) {
      config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializer);
    }

    if (StringUtils.isNotBlank(groupId)) {
      config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
    }

    if (null != enableAutoCommit) {
      config.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, enableAutoCommit);
    }

    if (null != autoCommitInterval) {
      config.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, autoCommitInterval);
    }

    if (null != maxPollRecords) {
      config.put(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, maxPollRecords);
    }

    if (null != maxPollInterval) {
      config.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, maxPollInterval);
    }

    return config;
  }


}
