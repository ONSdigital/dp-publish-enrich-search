package com.github.onsdigital.index.enrichment.model.payload;

import com.beust.jcommander.internal.Lists;
import com.github.onsdigital.index.enrichment.model.Page;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fawks on 15/02/2017.
 */
public class UpdatePageDataPayloadTest {

    @Test
    public void testEquals() throws Exception {
        Page testPage = new Page();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(testPage)
                                                                    .setContent(Lists.newArrayList("testJsonPage"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(testPage)
                                                                  .setContent(Lists.newArrayList("testJsonPage"));
        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsOnPage() throws Exception {
        Page expectedPage = new Page();
        Page actualpage = new Page();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(expectedPage)
                                                                    .setContent(Lists.newArrayList("testJsonPage"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(actualpage)
                                                                  .setContent(Lists.newArrayList("testJsonPage"));
        assertEquals(expected, actual);
    }

    @Test
    public void testNotEqualsOnPage() throws Exception {
        Page expectedPage = new Page();
        Page actualpage = new Page().setId("1");
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(expectedPage)
                                                                    .setContent(Lists.newArrayList("testJsonPage"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(actualpage)
                                                                  .setContent(Lists.newArrayList("testJsonPage"));
        assertNotEquals(expected, actual);
    }

    @Test
    public void testNotEqualsOnJson() throws Exception {
        Page expectedPage = new Page();
        Page actualpage = new Page();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(expectedPage)
                                                                    .setContent(Lists.newArrayList("testJsonPage1"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(actualpage)
                                                                  .setContent(Lists.newArrayList("testJsonPage2"));
        assertNotEquals(expected, actual);
    }

    @Test
    public void testHashCode() throws Exception {
        Page testPage = new Page();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(testPage)
                                                                    .setContent(Lists.newArrayList("testJsonPage"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(testPage)
                                                                  .setContent(Lists.newArrayList("testJsonPage"));
        assertEquals(expected.hashCode(), actual.hashCode());

    }

    @Test
    public void getPageContentJson() throws Exception {
        String expectedJson = "testJson";
        assertEquals(expectedJson,
                     new UpdatePageDataPayload().setContent(Lists.newArrayList(expectedJson))
                                                .getContent()
                                                .get(0));
    }

    @Test
    public void getPage() throws Exception {
        Page testPage = new Page();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(testPage);
        assertEquals(testPage, expected.getPage());
    }

}