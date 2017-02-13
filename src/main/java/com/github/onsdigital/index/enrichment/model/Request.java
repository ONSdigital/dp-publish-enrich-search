package com.github.onsdigital.index.enrichment.model;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Marker interface that also holds the Jackson Configuration
 */
@JsonTypeInfo(include = JsonTypeInfo.As.WRAPPER_OBJECT,
              use = JsonTypeInfo.Id.NAME,defaultImpl = PipelineRequest.class)
public interface Request {
}
