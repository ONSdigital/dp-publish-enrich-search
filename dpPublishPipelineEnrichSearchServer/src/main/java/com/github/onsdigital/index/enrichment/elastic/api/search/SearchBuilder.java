package com.github.onsdigital.index.enrichment.elastic.api.search;

import com.github.onsdigital.index.enrichment.elastic.api.AbstractRequestBuilder;
import com.github.onsdigital.index.enrichment.elastic.api.Action;
import com.github.onsdigital.index.enrichment.elastic.api.PayloadBuilder;
import com.github.onsdigital.index.enrichment.elastic.api.Verb;
import com.github.onsdigital.index.enrichment.exception.MissingRequirementsException;
import com.github.onsdigital.index.enrichment.exception.NotSupportedException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.github.onsdigital.index.enrichment.elastic.api.ElasticParameters.FROM;
import static com.github.onsdigital.index.enrichment.elastic.api.ElasticParameters.QUERY;
import static com.github.onsdigital.index.enrichment.elastic.api.ElasticParameters.SIZE;
import static com.github.onsdigital.index.enrichment.elastic.api.ElasticParameters.STORED_FIELDS;

/**
 * Enable Client to build search queries
 */
public class SearchBuilder extends AbstractRequestBuilder<SearchBuilder> {


    private Query query;
    private List<String> storedFields;
    private Integer size;
    private Integer from;


    public Integer getSize() {
        return size;
    }

    public SearchBuilder setSize(final Integer size) {
        this.size = size;
        return this;
    }

    public Integer getFrom() {
        return from;
    }

    public SearchBuilder setFrom(final Integer from) {
        this.from = from;
        return this;
    }

    public List<String> getStoredFields() {
        return storedFields;
    }

    public SearchBuilder setStoredFields(final List<String> stored_fields) {
        this.storedFields = stored_fields;
        return this;
    }

    public SearchBuilder addStoredField(String field) {
        if (null == storedFields) {
            storedFields = new ArrayList();
        }
        storedFields.add(field);
        return this;
    }

    public Query getQuery() {
        return query;
    }

    public SearchBuilder setQuery(final Query query) {
        this.query = query;
        return this;
    }


    public Action getAction() {
        return Action.SEARCH;
    }

    @Override
    public SearchBuilder setId(final String id) {
        throw new NotSupportedException("This method is not implemented for SearchBuilder");
    }

    @Override
    protected void validate() throws MissingRequirementsException {
        StringBuilder sb = new StringBuilder("|");

        if (null == getElasticClient()) {
            sb.append(" Missing ElasticClient |");
        }

        if (sb.length() > 1) {
            throw new MissingRequirementsException(sb.toString());
        }

    }
    @Override
    protected Verb getVerb() {
        return Verb.POST;
    }


    protected Map<String, Object> buildPayload() {
        PayloadBuilder<String,Object> payload = new PayloadBuilder<>();

        payload.put(STORED_FIELDS.getText(), getStoredFields());
        payload.put(SIZE.getText(), getSize());
        payload.put(FROM.getText(), getFrom());
        payload.put(QUERY.getText(), getQuery());
        return payload.build();
    }

    @Override
    protected Map<String, String> getParameters() {
        return null;
    }
}