package com.github.onsdigital.index.enrichment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.model.PipelineRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by fawks on 13/02/2017.
 */
@Service
public class TransformService {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    public PipelineRequest transform(String json) throws IOException {
        return MAPPER.readValue(json, PipelineRequest.class);

    }
}
