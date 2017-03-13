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

    private List<HttpAddressConfig> httpAddresses;
    public List<HttpAddressConfig> getHttpAddresses() {
        return httpAddresses;
    }

    public ElasticConfig setHttpAddresses(final List<HttpAddressConfig> httpAddresses) {
        this.httpAddresses = httpAddresses;
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
                .append(getHttpAddresses(), that.getHttpAddresses())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getHttpAddresses())
                .toHashCode();
    }
}
