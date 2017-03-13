package com.github.onsdigital.index.enrichment.elastic.api.load;

import com.github.onsdigital.index.enrichment.elastic.api.AbstractRequestBuilder;
import com.github.onsdigital.index.enrichment.elastic.api.Action;
import com.github.onsdigital.index.enrichment.elastic.api.Verb;
import com.github.onsdigital.index.enrichment.exception.MissingRequirementsException;

import java.util.Map;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Builder class to create Request to load a single page.
 */
public class LoadBuilder extends AbstractRequestBuilder<LoadBuilder> {

    @Override
    protected Action getAction() {
        return null;
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

        if (sb.length() > 1) {
            throw new MissingRequirementsException(sb.toString());
        }

    }

    @Override
    protected Verb getVerb() {
        return Verb.GET;
    }

    @Override
    protected Map<String, Object> buildPayload() {
        return null;
    }

    @Override
    protected Map<String, String> getParameters() {
        return null;
    }
}
