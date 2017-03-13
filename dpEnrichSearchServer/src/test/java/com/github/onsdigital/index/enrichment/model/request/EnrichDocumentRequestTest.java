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
 * Test.
 */
public class EnrichDocumentRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichDocumentRequestTest.class);

    @Test
    public void testFileLocation() throws Exception {
        String expected = "testFileLocation";
        assertEquals(expected,
                     new EnrichPageRequest().setFileLocation(expected)
                                            .getFileLocation());
    }

    @Test
    public void testFileContent() throws Exception {
        String expected = "testFileContent";
        assertEquals(expected,
                     new EnrichPageRequest().setFileContent(expected)
                                            .getFileContent());
    }


    @Test
    public void testEquals() throws Exception {
        String testFileLocation = "testFileLocation";
        String content = "testContent";
        assertEquals(buildRequest(testFileLocation, content), buildRequest(testFileLocation, content));
    }

    @Test
    public void testEqualsFalseOnContent() throws Exception {

        String testContent = "json:fileContent";
        assertNotEquals(buildRequest("uri1", testContent), buildRequest("uri2", testContent));
    }

    @Test
    public void testEqualsFalseOnUri() throws Exception {
        String testFileLocation = "testFileLocation";
        assertNotEquals(buildRequest(testFileLocation, "test1"), buildRequest(testFileLocation, "test2"));
    }


    @Test
    public void testHashCode() throws Exception {
        String testFileLocation = "testFileLocation";
        String testContent = "json:fileContent";
        assertEquals(buildRequest(testFileLocation, testContent).hashCode(), buildRequest(testFileLocation, testContent).hashCode());
    }

    @Test
    public void testJsonSerialisation() throws IOException {
        String testFileLocation = "testFileLocation";
        String testContent = "json:fileContent";
        EnrichPageRequest expected = buildRequest(testFileLocation, testContent);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(expected);
        LOGGER.info("testJsonSerialisation([]) : request Serialized to '{}'", json);
        EnrichPageRequest actual = mapper.readValue(json, EnrichPageRequest.class);
        assertEquals(expected, actual);
    }

    private EnrichPageRequest buildRequest(final String fileLocation, final String fileContent) {
        return new EnrichPageRequest().setFileLocation(fileLocation)
                                      .setFileContent(fileContent);
    }
}