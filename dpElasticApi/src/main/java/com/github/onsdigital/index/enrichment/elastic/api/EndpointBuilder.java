package com.github.onsdigital.index.enrichment.elastic.api;

import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Endpoint Builder is used to build the URL context (excluding the host and port name).
 * <p>
 * It enables the call to provide the <code>Indexz/<i>alias</i></code>, <code>type</code> and <code>id</code> <br/>
 * plus the Elastic action (<i><code>search/update</code></i> and any parameters (such as version=true).
 * <p/>
 * To Use you buildup the state of the EndpointBuilder instance and then request <code>build</code>
 * <pre>
 *    {@code
 * String endpoint = new EndpointBuilder().setId("ons")
 * .setType("document")
 * .setAction(Action.SEARCH)
 * .build();
 *     }
 * </pre>
 */
public class EndpointBuilder implements Builder<String> {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointBuilder.class);

    private Map<String, String> parameters;
    private String index;
    private String type;
    private String id;
    private Action action;


    public String getIndex() {
        return index;
    }

    public EndpointBuilder setIndex(final String index) {
        this.index = index;
        return this;
    }

    public String getType() {
        return type;
    }

    public EndpointBuilder setType(final String type) {
        this.type = type;
        return this;
    }

    public String getId() {
        return id;
    }

    public EndpointBuilder setId(final String id) {
        if (StringUtils.isNotBlank(id)) {
            try {
                this.id = java.net.URLEncoder.encode(id, "ISO-8859-1");
            }
            catch (UnsupportedEncodingException e) {
                LOGGER.warn("Failed to escape {}", id);
                this.id = id;
            }
        }
        return this;
    }


    public Action getAction() {
        return action;
    }

    public EndpointBuilder setAction(final Action action) {
        this.action = action;
        return this;
    }

    public String build() {

        String endpoint = buildBaseEndpoint();

        if (null != getAction()) {
            endpoint = endpoint + getAction().getAction();
        }

        if (MapUtils.isNotEmpty(getParameters())) {
            endpoint = buildParameters(endpoint);
        }

        return endpoint;
    }

    private String buildParameters(String endpoint) {
        String params = getParameters().entrySet()
                                       .stream()
                                       .map(k -> String.format("%1$s=%2$s", k.getKey(), k.getValue()))
                                       .collect(Collectors.joining("&amp;"));
        endpoint = endpoint + "?" + params;
        return endpoint;
    }

    private String buildBaseEndpoint() {
        final String endpoint;
        if (StringUtils.isBlank(index)) {
            endpoint = "/";
        }
        else if (StringUtils.isBlank(type)) {
            endpoint = String.format("/%1$s/", index);
        }

        else if (StringUtils.isBlank(id)) {
            endpoint = String.format("/%1$s/%2$s/", index, type);
        }
        else {
            endpoint = String.format("/%1$s/%2$s/%3$s/", index, type, id);
        }
        return endpoint;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public EndpointBuilder setParameters(final Map<String, String> parameters) {
        this.parameters = parameters;
        return this;
    }
}
