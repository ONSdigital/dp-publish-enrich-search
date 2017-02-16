package com.github.onsdigital.index.enrichment.model.payload;

import com.github.onsdigital.index.enrichment.model.Page;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fawks on 15/02/2017.
 */
public class UpdateResourcePayloadTest {

    @Test
    public void testEquals() throws Exception {
        Page testPage = new Page();
        UpdateResourcePayload expected = new UpdateResourcePayload().setPage(testPage)
                                                                    .setUri("testS3Location");
        UpdateResourcePayload actual = new UpdateResourcePayload().setPage(testPage)
                                                                  .setUri("testS3Location");
        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsOnPage() throws Exception {
        Page expectedPage = new Page();
        Page actualpage = new Page();
        UpdateResourcePayload expected = new UpdateResourcePayload().setPage(expectedPage)
                                                                    .setUri("testS3Location");
        UpdateResourcePayload actual = new UpdateResourcePayload().setPage(actualpage)
                                                                  .setUri("testS3Location");
        assertEquals(expected, actual);
    }

    @Test
    public void testNotEqualsOnPage() throws Exception {
        Page expectedPage = new Page();
        Page actualpage = new Page().setId("1");
        UpdateResourcePayload expected = new UpdateResourcePayload().setPage(expectedPage)
                                                                    .setUri("testS3Location");
        UpdateResourcePayload actual = new UpdateResourcePayload().setPage(actualpage)
                                                                  .setUri("testS3Location");
        assertNotEquals(expected, actual);
    }

    @Test
    public void testNotEqualsOnJson() throws Exception {
        Page expectedPage = new Page();
        Page actualpage = new Page();
        UpdateResourcePayload expected = new UpdateResourcePayload().setPage(expectedPage)
                                                                    .setUri("testS3Location1");
        UpdateResourcePayload actual = new UpdateResourcePayload().setPage(actualpage)
                                                                  .setUri("testS3Location2");
        assertNotEquals(expected, actual);
    }

    @Test
    public void testHashCode() throws Exception {
        Page testPage = new Page();
        UpdateResourcePayload expected = new UpdateResourcePayload().setPage(testPage)
                                                                    .setUri("testS3Location");
        UpdateResourcePayload actual = new UpdateResourcePayload().setPage(testPage)
                                                                  .setUri("testS3Location");
        assertEquals(expected.hashCode(), actual.hashCode());

    }

    @Test
    public void testS3Location() throws Exception {
        assertEquals("testS3Location",
                     new UpdateResourcePayload().setUri("testS3Location")
                                                .getUri());
    }

    @Test
    public void getPage() throws Exception {
        Page testPage = new Page();
        UpdateResourcePayload expected = new UpdateResourcePayload().setPage(testPage);
        assertEquals(testPage, expected.getPage());
    }

}