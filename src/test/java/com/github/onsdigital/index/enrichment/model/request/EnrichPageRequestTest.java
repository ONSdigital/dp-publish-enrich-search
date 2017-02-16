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
public class EnrichPageRequestTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichPageRequestTest.class);

    @Test
    public void testUri() throws Exception {
        String expected = "testUri";
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
        String testUri = "testUri";
        String content = "testContent";
        assertEquals(buildRequest(testUri, content), buildRequest(testUri, content));
    }

    @Test
    public void testEqualsFalseOnContent() throws Exception {

        String testContent = "json:fileContent";
        assertNotEquals(buildRequest("uri1", testContent), buildRequest("uri2", testContent));
    }

    @Test
    public void testEqualsFalseOnUri() throws Exception {
        String testUri = "testUri";
        assertNotEquals(buildRequest(testUri, "test1"), buildRequest(testUri, "test2"));
    }


    @Test
    public void testHashCode() throws Exception {
        String testUri = "testUri";
        String testContent = "json:fileContent";
        assertEquals(buildRequest(testUri, testContent).hashCode(), buildRequest(testUri, testContent).hashCode());
    }

    @Test
    public void testJsonSerialisation() throws IOException {
        String testUri = "testUri";
        String testContent = "json:fileContent";
        EnrichPageRequest expected = buildRequest(testUri, testContent);
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        String json = mapper.writeValueAsString(expected);
        LOGGER.info("testJsonSerialisation([]) : request Serialized to '{}'", json);
        EnrichPageRequest actual = mapper.readValue(json, EnrichPageRequest.class);
        assertEquals(expected, actual);
    }

    private EnrichPageRequest buildRequest(final String uri, final String fileContent) {
        return new EnrichPageRequest().setFileLocation(uri)
                                      .setFileContent(fileContent);
    }
}