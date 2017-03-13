package com.github.onsdigital.index.enrichment.model.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * test
 */
public class EnrichResourceRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichResourceRequestTest.class);

    @Test
    public void testGetS3Location() throws Exception {
        String expected = "testS3Loc";
        assertEquals(expected,
                     new EnrichResourceRequest().setS3Location(expected)
                                                .getS3Location());

    }

    @Test
    public void testGetFileLocation() throws Exception {
        String expected = "testFileLocation";
        assertEquals(expected,
                     new EnrichResourceRequest().setFileLocation(expected)
                                                .getFileLocation());
    }

    @Test
    public void testEquals() throws Exception {
        String testFileLocation = "testFileLocation";
        String testS3 = "s3Loc";
        assertEquals(buildRequest(testFileLocation, testS3), buildRequest(testFileLocation, testS3));
    }

    @Test
    public void testEqualsFalseOnS3() throws Exception {
        String testS3 = "s3Loc";
        assertNotEquals(buildRequest("fileLocation", testS3), buildRequest("fileLocation2", testS3));
    }

    @Test
    public void testEqualsFalseOnFileLocation() throws Exception {
        String testFileLocation = "testFileLocation";
        assertNotEquals(buildRequest(testFileLocation, "s3loc1"), buildRequest(testFileLocation, "s3Loc2"));
    }


    @Test
    public void testHashCode() throws Exception {
        String testFileLocation = "testFileLocation";
        String testS3 = "s3Loc";
        assertEquals(buildRequest(testFileLocation, testS3).hashCode(), buildRequest(testFileLocation, testS3).hashCode());
    }

    @Test
    public void testJsonSerialisation() throws IOException {
        String testFileLocation = "testFileLocation";
        String testS3 = "s3://s3Loc";
        EnrichResourceRequest expected = buildRequest(testFileLocation, testS3);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(expected);
        LOGGER.info("testJsonSerialisation([]) : request Serialized to '{}'", json);
        EnrichResourceRequest actual = mapper.readValue(json, EnrichResourceRequest.class);

        assertEquals(expected, actual);


    }

    private EnrichResourceRequest buildRequest(final String testFileLocation, final String testS3) {
        return new EnrichResourceRequest().setFileLocation(testFileLocation)
                                          .setS3Location(testS3);
    }
}
