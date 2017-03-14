package com.github.onsdigital.index.enrichment.elastic.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import com.github.onsdigital.index.enrichment.exception.MissingRequirementsException;
import org.apache.http.HttpEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.RestClient;

import java.util.Map;

/**
 * Abstract Builder to do the grunt work and delegate to child only when specifics required
 */
public abstract class AbstractRequestBuilder<C> implements Builder<Request> {
    private static final ObjectMapper MAPPER = new ObjectMapper().configure(SerializationFeature.INDENT_OUTPUT, true)
                                                                 .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    private String id;
    private String index;
    private String type;
    private RestClient elasticClient;

    protected RestClient getElasticClient() {
        return elasticClient;
    }

    public C setElasticClient(final RestClient elasticClient) {
        this.elasticClient = elasticClient;
        return (C) this;
    }

    protected abstract Action getAction();


    protected String getId() {
        return id;
    }

    public C setId(final String id) {
        this.id = id;
        return (C) this;
    }

    protected String getIndex() {
        return index;
    }

    public C setIndex(final String index) {
        this.index = index;
        return (C) this;
    }

    protected String getType() {
        return type;
    }

    public C setType(final String type) {
        this.type = type;
        return (C) this;
    }

    public Request build() throws ElasticSearchException {
        validate();
        return new Request().setVerb(getVerb())
                            .setEndpoint(buildEndpoint())
                            .setPayload(buildHttpEntity(buildPayload()))
                            .setElasticClient(getElasticClient());

    }

    protected abstract void validate() throws MissingRequirementsException;

    protected abstract Verb getVerb();

    protected abstract Map<String, Object> buildPayload();

    private String buildEndpoint() {
        return new EndpointBuilder().setId(getId())
                                    .setType(getType())
                                    .setIndex(getIndex())
                                    .setAction(getAction())
                                    .setParameters(getParameters())
                                    .build();
    }

    protected abstract Map<String, String> getParameters();

    private HttpEntity buildHttpEntity(Map<String, Object> payload) throws ElasticSearchException {
        try {
            final String s = MAPPER.writeValueAsString(payload);
            return new NStringEntity(s, ContentType.APPLICATION_JSON);
        }
        catch (JsonProcessingException e) {
            throw new ElasticSearchException("Could not serialize query in format for querying", e);
        }
    }

}
