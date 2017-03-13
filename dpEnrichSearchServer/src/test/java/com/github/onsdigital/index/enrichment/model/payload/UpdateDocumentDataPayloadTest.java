package com.github.onsdigital.index.enrichment.model.payload;

import com.beust.jcommander.internal.Lists;
import com.github.onsdigital.index.enrichment.model.Document;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Created by fawks on 15/02/2017.
 */
public class UpdateDocumentDataPayloadTest {

    @Test
    public void testEquals() throws Exception {
        Document testDocument = new Document();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(testDocument)
                                                                    .setContent(Lists.newArrayList("testJsonPage"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(testDocument)
                                                                  .setContent(Lists.newArrayList("testJsonPage"));
        assertEquals(expected, actual);
    }

    @Test
    public void testEqualsOnPage() throws Exception {
        Document expectedDocument = new Document();
        Document actualpage = new Document();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(expectedDocument)
                                                                    .setContent(Lists.newArrayList("testJsonPage"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(actualpage)
                                                                  .setContent(Lists.newArrayList("testJsonPage"));
        assertEquals(expected, actual);
    }

    @Test
    public void testNotEqualsOnPage() throws Exception {
        Document expectedDocument = new Document();
        Document actualpage = new Document().setId("1");
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(expectedDocument)
                                                                    .setContent(Lists.newArrayList("testJsonPage"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(actualpage)
                                                                  .setContent(Lists.newArrayList("testJsonPage"));
        assertNotEquals(expected, actual);
    }

    @Test
    public void testNotEqualsOnJson() throws Exception {
        Document expectedDocument = new Document();
        Document actualpage = new Document();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(expectedDocument)
                                                                    .setContent(Lists.newArrayList("testJsonPage1"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(actualpage)
                                                                  .setContent(Lists.newArrayList("testJsonPage2"));
        assertNotEquals(expected, actual);
    }

    @Test
    public void testHashCode() throws Exception {
        Document testDocument = new Document();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(testDocument)
                                                                    .setContent(Lists.newArrayList("testJsonPage"));
        UpdatePageDataPayload actual = new UpdatePageDataPayload().setPage(testDocument)
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
        Document testDocument = new Document();
        UpdatePageDataPayload expected = new UpdatePageDataPayload().setPage(testDocument);
        assertEquals(testDocument, expected.getDocument());
    }

}