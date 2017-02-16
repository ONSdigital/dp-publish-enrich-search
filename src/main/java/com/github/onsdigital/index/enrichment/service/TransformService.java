package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.exception.EnrichServiceException;
import com.github.onsdigital.index.enrichment.model.request.PipelineRequest;
import org.springframework.stereotype.Service;

import static com.github.onsdigital.index.enrichment.model.request.PipelineRequestBuilder.buildRequests;

/**
 * Transforms the Json String into the inbound message request instance.<br/>
 * This was originally in the DSL <code>.transform()</code> but the exception was not being handled correctly, so,
 * I have extract in to a Service component
 */
@Service
public class TransformService {
    public PipelineRequest transform(String json) throws EnrichServiceException {
        return buildRequests(json);
    }

}
