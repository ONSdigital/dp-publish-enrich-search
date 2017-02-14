package com.github.onsdigital.index.enrichment.model;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by fawks on 13/02/2017.
 */
public class PipelineRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineRequestTest.class);

    @Test
    public void getCollectionId() throws Exception {
        PipelineRequest r = new PipelineRequest();
        String testCollectionId = "testCollectionId";
        assertEquals(testCollectionId,
                     r.setCollectionId(testCollectionId)
                      .getCollectionId());

    }

    @Test
    public void getFileLocation() throws Exception {
        PipelineRequest r = new PipelineRequest();
        String testFileLocation = "testFileLocation";
        assertEquals(testFileLocation,
                     r.setFileLocation(testFileLocation)
                      .getFileLocation());
    }

    @Test
    public void getS3Location() throws Exception {
        PipelineRequest r = new PipelineRequest();
        String testS3Location = "testS3Location";
        assertEquals(testS3Location,
                     r.setS3Location(testS3Location)
                      .getS3Location());
    }


    @Test
    public void serialize() throws IOException {
        PipelineRequest expected = new PipelineRequest().setCollectionId("abc")
                                                        .setS3Location("s3://bucket/package/file.json")
                                                        .setFileLocation("file://Test/file/location");

        ObjectMapper objectMapper = new ObjectMapper();
        String s = objectMapper.writeValueAsString(expected);
        LOGGER.info("serialize([]) : {}", s);
        PipelineRequest actual = objectMapper.readValue(s, PipelineRequest.class);
        assertEquals(expected, actual);

    }


    @Test
    public void deserialize() throws IOException {
        String expected = "{\"collectionId\":\"abc\",\"fileLocation\":\"file://Test/file/location\",\"s3Location\":\"s3://bucket/package/file.json\"}";
        ObjectMapper objectMapper = new ObjectMapper();
        PipelineRequest object = objectMapper.readValue(expected, PipelineRequest.class);
        String actual = objectMapper.writeValueAsString(object);
        assertEquals(expected, actual);
    }
}