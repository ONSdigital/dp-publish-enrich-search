package com.github.onsdigital.index.enrichment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.model.request.EnrichPageRequest;
import com.github.onsdigital.index.enrichment.model.request.EnrichResourceRequest;
import com.github.onsdigital.index.enrichment.model.request.PipelineRequest;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by fawks on 16/02/2017.
 */
public class TransformServiceTest {

    @Test
    public void transformEnrichPageRequest() throws Exception {
        EnrichPageRequest expectedRequest = new EnrichPageRequest().setFileContent("TestFileLocation")
                                                                   .setFileLocation("testFileLocation");

        String requestJson = new ObjectMapper().writeValueAsString(expectedRequest);
        PipelineRequest actualRequest = new TransformService().transform(requestJson);
        assertEquals(expectedRequest, actualRequest);

    }

    @Test
    public void transformEnrichResourceRequest() throws Exception {

        EnrichResourceRequest expectedRequest = new EnrichResourceRequest().setS3Location("TestS3Location")
                                                                           .setFileLocation("testFileLocation");

        String requestJson = new ObjectMapper().writeValueAsString(expectedRequest);
        PipelineRequest actualRequest = new TransformService().transform(requestJson);
        assertEquals(expectedRequest, actualRequest);

    }
}