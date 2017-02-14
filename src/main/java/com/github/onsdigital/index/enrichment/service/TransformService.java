package com.github.onsdigital.index.enrichment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.model.PipelineRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Transforms the Json String into the inbound message request instance.<br/>
 * This was originally in the DSL <code>.transform()</code> but the exception was not being handled correctly, so,
 * I have extract in to a Service component
 */
@Service
public class TransformService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public PipelineRequest transform(String json) throws IOException {
        return MAPPER.readValue(json, PipelineRequest.class);

    }
}
