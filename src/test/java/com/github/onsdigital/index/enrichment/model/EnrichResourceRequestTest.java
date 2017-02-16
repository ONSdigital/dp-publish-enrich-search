package com.github.onsdigital.index.enrichment.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.model.request.EnrichResourceRequest;
import com.github.onsdigital.index.enrichment.model.request.PipelineRequest;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * test.
 */
public class EnrichResourceRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichResourceRequestTest.class);


    @Test
    public void getFileLocation() throws Exception {
        EnrichResourceRequest r = new EnrichResourceRequest();
        String testFileLocation = "testFileLocation";
        assertEquals(testFileLocation,
                     r.setUri(testFileLocation)
                      .getUri());
    }

    @Test
    public void getS3Location() throws Exception {
        EnrichResourceRequest r = new EnrichResourceRequest();
        String testS3Location = "testS3Location";
        assertEquals(testS3Location,
                     r.setS3Location(testS3Location)
                      .getS3Location());
    }


    @Test
    public void serialize() throws IOException {
        EnrichResourceRequest expected = new EnrichResourceRequest().setS3Location("s3://bucket/package/file.json")
                                                                    .setUri("/Test/file/location");

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(expected);
        LOGGER.info("serialize([]) : {}", s);
        PipelineRequest actual = objectMapper.readValue(s, EnrichResourceRequest.class);
        assertEquals(expected, actual);

    }


}