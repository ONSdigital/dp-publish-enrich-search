package com.github.onsdigital.index.enrichment.elastic.api.update;

import com.github.onsdigital.index.enrichment.elastic.api.AbstractRequestBuilder;
import com.github.onsdigital.index.enrichment.elastic.api.Action;
import com.github.onsdigital.index.enrichment.elastic.api.Verb;
import com.github.onsdigital.index.enrichment.exception.MissingRequirementsException;
import org.apache.commons.collections4.MapUtils;

import java.util.HashMap;
import java.util.Map;

import static com.github.onsdigital.index.enrichment.elastic.api.ElasticParameters.DOC;
import static com.github.onsdigital.index.enrichment.elastic.api.ElasticParameters.UPSERT;
import static com.github.onsdigital.index.enrichment.elastic.api.ElasticParameters.VERSION;
import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by guidof on 09/03/17.
 */
public class UpdateBuilder extends AbstractRequestBuilder<UpdateBuilder> {

    private Map<String, Object> doc;
    private Map<String, Object> upsert;
    private Long version;

    public Long getVersion() {
        return version;
    }

    public UpdateBuilder setVersion(final Long version) {
        this.version = version;
        return this;
    }

    @Override
    protected Action getAction() {
        return Action.UPDATE;
    }


    @Override
    protected void validate() throws MissingRequirementsException {
        StringBuilder sb = new StringBuilder("|");

        if (null == getElasticClient()) {
            sb.append(" Missing ElasticClient |");
        }

        if (isBlank(getId())) {
            sb.append(" Missing Document Id |");
        }

        if (isBlank(getType())) {
            sb.append(" Missing Document Type |");
        }

        if (isBlank(getIndex())) {
            sb.append(" Missing Document Index |");
        }

        if (MapUtils.isEmpty(getDoc())) {
            sb.append(" Missing Document details to add |");
        }
        if (sb.length() > 1) {
            throw new MissingRequirementsException(sb.toString());
        }

    }

    @Override
    protected Verb getVerb() {
        return Verb.POST;
    }

    public Map<String, Object> getDoc() {
        return doc;
    }

    public UpdateBuilder setDoc(final Map<String, Object> doc) {
        this.doc = doc;
        return this;
    }

    public Map<String, Object> getUpsert() {
        return upsert;
    }

    public UpdateBuilder setUpsert(final Map<String, Object> upsert) {
        this.upsert = upsert;
        return this;
    }

    @Override
    protected Map<String, Object> buildPayload() {
        Map<String, Object> p = new HashMap<>();
        p.put(DOC.getText(), getDoc());
        p.put(UPSERT.getText(), getUpsert());
        return p;
    }

    @Override
    protected Map<String, String> getParameters() {
        Map<String, String> p = new HashMap<>();
        if (null != version) {
            p.put(VERSION.getText(), version.toString());
        }
        return p;
    }
}