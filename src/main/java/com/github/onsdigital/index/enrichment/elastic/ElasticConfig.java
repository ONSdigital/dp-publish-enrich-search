package com.github.onsdigital.index.enrichment.elastic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

    public ElasticConfig setInetAddresses(final List<InetAddressConfig> inetAddresses) {
        this.inetAddresses = inetAddresses;
        return this;
    }

    public String getClusterName() {
        return clusterName;
    }

    public ElasticConfig setClusterName(final String clusterName) {
        this.clusterName = clusterName;
        return this;

    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final ElasticConfig that = (ElasticConfig) o;

        return new EqualsBuilder()
                .append(getInetAddresses(), that.getInetAddresses())
                .append(getClusterName(), that.getClusterName())
                .append(ignoreClusterName, that.ignoreClusterName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getInetAddresses())
                .append(getClusterName())
                .append(ignoreClusterName)
                .toHashCode();
    }
}
