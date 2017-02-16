package com.github.onsdigital.index.enrichment.model.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.exception.EnrichServiceException;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

/**
 * Created by fawks on 14/02/2017.
 */
public class PipelineRequestBuilderTest {
    @Test
    public void testEnrichResourceRequest() throws EnrichServiceException {
        ObjectMapper mapper = new ObjectMapper();
        PipelineRequest pipelineRequest = PipelineRequestBuilder.buildRequests(
                "{ \"collectionId\":\"test-0001\", \"uri\":\"testLocation\", \"fileLocation\": \"/about/data.json\", \"fileContent\": \"{uri:/test}\" }");
        assertNotNull(pipelineRequest);

    }

    @Test
    public void testEnrichPageRequest() throws EnrichServiceException {
        ObjectMapper mapper = new ObjectMapper();
        PipelineRequest pipelineRequest = PipelineRequestBuilder.buildRequests(
                "{ \"collectionId\":\"test-0001\", \"uri\": \"/about/data\", \"s3Location\": \"file:localhost\" }");
        assertNotNull(pipelineRequest);

    }

    @Test
    public void testEnrichPageRequestIgnoreExtraField() throws EnrichServiceException {
        ObjectMapper mapper = new ObjectMapper();
        PipelineRequest pipelineRequest = PipelineRequestBuilder.buildRequests(
                "{ \"collectionId\":\"test-0001\", \"uri\": \"/about/data\", \"s3Location\": \"file:localhost\",\"anotherField\":\"ignoreme\" }");
        assertNotNull(pipelineRequest);
    }


    @Test(expected = EnrichServiceException.class)
    public void testEnrichPageRequestNoURIField() throws EnrichServiceException {
        ObjectMapper mapper = new ObjectMapper();
        PipelineRequest pipelineRequest = PipelineRequestBuilder.buildRequests(
                "{ \"collectionId\":\"test-0001\", \"s3Location\": \"file:localhost\",\"anotherField\":\"ignoreme\" }");
        assertNotNull(pipelineRequest);
    }

    @Test(expected = EnrichServiceException.class)
    public void testEnrichPageRequestNoDetectableFields() throws EnrichServiceException {
        ObjectMapper mapper = new ObjectMapper();
        PipelineRequest pipelineRequest = PipelineRequestBuilder.buildRequests(
                "{ \"collectionId\":\"test-0001\", \"anotherField\":\"ignoreme\" }");
        assertNotNull(pipelineRequest);
    }
}