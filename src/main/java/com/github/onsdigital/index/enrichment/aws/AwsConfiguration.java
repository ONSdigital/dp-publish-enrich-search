package com.github.onsdigital.index.enrichment.aws;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * AWS Configuration information
 */
@Component
@ConfigurationProperties(prefix = "cloud.aws.credentials")
public class AwsConfiguration {

  private String accessKey;
  private String secretKey;

  public String getAccessKey() {
    return accessKey;
  }

  public AwsConfiguration setAccessKey(final String accessKey) {
    this.accessKey = accessKey;
    return this;
  }

  public String getSecretKey() {
    return secretKey;
  }

  public AwsConfiguration setSecretKey(final String secretKey) {
    this.secretKey = secretKey;
    return this;
  }
}
