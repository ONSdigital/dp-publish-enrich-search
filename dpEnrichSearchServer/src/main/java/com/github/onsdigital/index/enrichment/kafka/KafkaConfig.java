package com.github.onsdigital.index.enrichment.kafka;

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

    private Integer consumers;
    private ZookeeperConfig zookeeper = new ZookeeperConfig();

    public Integer getConsumers() {
        return consumers;
    }

    public KafkaConfig setConsumers(final Integer consumers) {
        this.consumers = consumers;
        return this;
    }

    public String getTopic() {
        return topic;
    }

    public KafkaConfig setTopic(final String topic) {
        this.topic = topic;
        return this;
    }

    public String getMessageKey() {
        return messageKey;
    }

    public KafkaConfig setMessageKey(final String messageKey) {
        this.messageKey = messageKey;
        return this;
    }

    public Boolean getAutoStart() {
        return autoStart;
    }

    public KafkaConfig setAutoStart(final Boolean autoStart) {
        this.autoStart = autoStart;
        return this;

    }

    public ZookeeperConfig getZookeeper() {
        return zookeeper;
    }

    public KafkaConfig setZookeeper(final ZookeeperConfig zookeeper) {
        this.zookeeper = zookeeper;
        return this;
    }

    public static class ZookeeperConfig {
        private String connect;

        public String getConnect() {
            return connect;
        }

        public ZookeeperConfig setConnect(final String connect) {
            this.connect = connect;
            return this;
        }
    }
}