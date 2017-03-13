package com.github.onsdigital.index.enrichment.elastic.api;

import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import org.apache.http.HttpEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Created by guidof on 09/03/17.
 */
public class Request {
    private static final Logger LOGGER = LoggerFactory.getLogger(Request.class);
    private Verb verb;

    private HttpEntity payload;
    private String endpoint;
    private RestClient elasticClient;
    private Map<String, String> headers = Collections.emptyMap();

    public Verb getVerb() {
        return verb;
    }

    public Request setVerb(final Verb verb) {
        this.verb = verb;
        return this;
    }

    public RestClient getElasticClient() {
        return elasticClient;
    }

    public Request setElasticClient(final RestClient elasticClient) {
        this.elasticClient = elasticClient;
        return this;
    }

    public HttpEntity getPayload() {
        return payload;
    }

    public Request setPayload(final HttpEntity payload) {
        this.payload = payload;
        return this;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public Request setEndpoint(final String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    public Response execute() throws ElasticSearchException {
        try {
            return elasticClient.performRequest(verb.getVerb(), endpoint, headers, payload);
        }
        catch (IOException io) {
            LOGGER.info("execute([]) :Failed to execute request against 'action {} :endpoint: {} payload: {}'",
                        verb,
                        endpoint,
                        payload);
            throw new ElasticSearchException("Failed to execute request against", io);

        }
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Request setHeaders(final Map<String, String> headers) {
        this.headers = headers;
        return this;
    }
}
