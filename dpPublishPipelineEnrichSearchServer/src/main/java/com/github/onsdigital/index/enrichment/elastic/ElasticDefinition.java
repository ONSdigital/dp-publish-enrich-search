package com.github.onsdigital.index.enrichment.elastic;


import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.UnknownHostException;


/**
 * Elasticsearch Setup
 *
 * @author James Fawke
 */
@Configuration

public class ElasticDefinition {


    @Bean
    public RestClient elasticClient(ElasticConfig config) throws UnknownHostException {


        RestClient client = RestClient.builder(buildHosts(config))
                                      .build();


        return client;

    }

    private HttpHost[] buildHosts(ElasticConfig config) {
        return config.getHttpAddresses()
                     .stream()
                     .map(cfg -> new HttpHost(cfg.getHost(), cfg.getPort(), cfg.getScheme()))
                     .toArray(size -> new HttpHost[size]);

    }


}
