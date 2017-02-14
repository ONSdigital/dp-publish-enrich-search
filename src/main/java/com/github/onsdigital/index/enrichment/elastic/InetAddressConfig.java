package com.github.onsdigital.index.enrichment.elastic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        final InetAddressConfig that = (InetAddressConfig) o;

        return new EqualsBuilder()
                .append(getHost(), that.getHost())
                .append(getPort(), that.getPort())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getHost())
                .append(getPort())
                .toHashCode();
    }
}
