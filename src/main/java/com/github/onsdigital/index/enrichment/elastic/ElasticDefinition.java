package com.github.onsdigital.index.enrichment.elastic;


import org.apache.commons.lang3.BooleanUtils;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetAddress;
import java.net.UnknownHostException;


/**
 * Elasticsearch Setup
 *
 * @author James Fawke
 */
@Configuration

public class ElasticDefinition {

    private TransportClient client;


    @Bean
    public TransportClient elasticClient(ElasticConfig config) throws UnknownHostException {
        Settings settings = buildSettings(config);

        client = TransportClient.builder()
                                .settings(settings)
                                .build();
//Add transport addresses and do something with the client...
        for (InetAddressConfig addr : config.getInetAddresses()) {
            client.addTransportAddress(
                    new InetSocketTransportAddress(InetAddress.getByName(addr.getHost()), addr.getPort()));
        }

        return client;

    }

    private Settings buildSettings(final ElasticConfig config) {
        Settings.Builder builder = Settings.settingsBuilder();

        if (StringUtils.isNotBlank(config.getClusterName())) {
            builder.put("cluster.name", config.getClusterName());
        }

        if (BooleanUtils.isTrue(config.isIgnoreClusterName())) {
            builder.put("client.transport.ignore_cluster_name",
                        config.isIgnoreClusterName()
                              .booleanValue());
        }

        return builder.build();
    }

}
