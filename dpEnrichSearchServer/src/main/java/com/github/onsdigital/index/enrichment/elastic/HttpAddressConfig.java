package com.github.onsdigital.index.enrichment.elastic;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

/**
 * @author James Fawke
 */
public class HttpAddressConfig {
    private String host;
    private Integer port;
    private String scheme;

    public String getHost() {
        return host;
    }

    public HttpAddressConfig setHost(final String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public HttpAddressConfig setPort(final Integer port) {
        this.port = port;
        return this;
    }

    public String getScheme() {
        return scheme;
    }

    public HttpAddressConfig setScheme(final String scheme) {
        this.scheme = scheme;
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

        final HttpAddressConfig that = (HttpAddressConfig) o;

        return new EqualsBuilder()
                .append(getHost(), that.getHost())
                .append(getPort(), that.getPort())
                .append(getScheme(), that.getScheme())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getHost())
                .append(getPort())
                .append(getScheme())
                .toHashCode();
    }


}
