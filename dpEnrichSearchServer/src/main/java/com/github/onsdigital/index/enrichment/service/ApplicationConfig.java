package com.github.onsdigital.index.enrichment.service;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Created by guidof on 13/03/17.
 */
@ConfigurationProperties(prefix = "enrichmentService")
@Component
public class ApplicationConfig {

    RetryConfig retry = new RetryConfig();

    public RetryConfig getRetry() {
        return retry;
    }

    public ApplicationConfig setRetry(final RetryConfig retry) {
        this.retry = retry;
        return this;
    }

    public static class RetryConfig {

        private Long initialInterval;
        private Double multiplier;
        private Long maxInterval;
        private Integer maxAttempts;

        public Long getInitialInterval() {
            return initialInterval;
        }

        public void setInitialInterval(final Long initialInterval) {
            this.initialInterval = initialInterval;
        }

        public Double getMultiplier() {
            return multiplier;
        }

        public void setMultiplier(final Double multiplier) {
            this.multiplier = multiplier;
        }

        public Long getMaxInterval() {
            return maxInterval;
        }

        public void setMaxInterval(final Long maxInterval) {
            this.maxInterval = maxInterval;
        }

        public Integer getMaxAttempts() {
            return maxAttempts;
        }

        public void setMaxAttempts(final Integer maxAttempts) {
            this.maxAttempts = maxAttempts;
        }
    }
}