package com.github.onsdigital.index.enrichment.elastic.api;

import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Collections;
import java.util.Map;

/**
 * Generic Elastic Search request; the action and the result are dependent on the <code>verb</code>, <code>endpoint</code>
 * and <code>payload</code>.<br/>
 * This object is designed in lines of a builder pattern where the state is built up and then the request is executed,
 * on calling <code>execute</code> the contents of teh request are used to invoke the request from ElasticSearch
 */
public class Request {
    public static final String FAILED_MESSAGE = "Failed to execute request as received error code %1$s with a message of %2$s";
    private static final Logger LOGGER = LoggerFactory.getLogger(Request.class);
    private Verb verb;
    private HttpEntity payload;
    private String endpoint;
    private RestClient elasticClient;
    private Map<String, String> headers = Collections.emptyMap();


    Request() {
        //Limit the construction of this class to the api package
    }

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
            final Response response = elasticClient.performRequest(verb.getVerb(), endpoint, headers, payload);
            final StatusLine statusLine = response.getStatusLine();
            if (null != statusLine) {
                final int statusCode = statusLine.getStatusCode();
                if (statusCode != 404 && statusCode != 200) {
                    final String message = String.format(FAILED_MESSAGE,
                                                         statusLine.getStatusCode(),
                                                         statusLine.getReasonPhrase());
                    throw new ElasticSearchException(message);
                }
            }
            return response;
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
