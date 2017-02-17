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
    public void testGetUri() throws Exception {
        String expected = "testUri";
        assertEquals(expected,
                     new EnrichResourceRequest().setFileLocation(expected)
                                                .getFileLocation());
    }

    @Test
    public void testEquals() throws Exception {
        String testUri = "testUri";
        String testS3 = "s3Loc";
        assertEquals(buildRequest(testUri, testS3), buildRequest(testUri, testS3));
    }

    @Test
    public void testEqualsFalseOnS3() throws Exception {
        String testS3 = "s3Loc";
        assertNotEquals(buildRequest("uri1", testS3), buildRequest("uri2", testS3));
    }

    @Test
    public void testEqualsFalseOnUri() throws Exception {
        String testUri = "testUri";
        assertNotEquals(buildRequest(testUri, "s3loc1"), buildRequest(testUri, "s3Loc2"));
    }


    @Test
    public void testHashCode() throws Exception {
        String testUri = "testUri";
        String testS3 = "s3Loc";
        assertEquals(buildRequest(testUri, testS3).hashCode(), buildRequest(testUri, testS3).hashCode());
    }

    @Test
    public void testJsonSerialisation() throws IOException {
        String testUri = "testUri";
        String testS3 = "s3://s3Loc";
        EnrichResourceRequest expected = buildRequest(testUri, testS3);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(expected);
        LOGGER.info("testJsonSerialisation([]) : request Serialized to '{}'", json);
        EnrichResourceRequest actual = mapper.readValue(json, EnrichResourceRequest.class);

        assertEquals(expected, actual);


    }

    private EnrichResourceRequest buildRequest(final String testUri, final String testS3) {
        return new EnrichResourceRequest().setFileLocation(testUri)
                                          .setS3Location(testS3);
    }
}
