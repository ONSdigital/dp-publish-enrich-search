package com.github.onsdigital.index.enrichment.service;

import com.beust.jcommander.internal.Lists;
import com.github.onsdigital.index.enrichment.model.Page;
import com.github.onsdigital.index.enrichment.model.payload.UpdatePageDataPayload;
import com.github.onsdigital.index.enrichment.model.payload.UpdateResourcePayload;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.onsdigital.index.enrichment.model.ModelEnum.CONTENT;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.DOWNLOADS;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.FILE;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.PAGEDATA;
import static org.junit.Assert.assertEquals;

/**
 * Test.
 */
public class EnrichmentServiceTest {

  EnrichmentService service = new EnrichmentService();

  @Test
  public void testEnrichPageUpdatePageDataPayload() throws IOException {
    Page page = new Page();

    UpdatePageDataPayload payload = new UpdatePageDataPayload();
    payload.setPage(page);
    List<String> testContent = Lists.newArrayList("TestContent");
    payload.setContent(testContent);


    Page enrichedPage = service.enrichPage(payload);
    assertEquals(testContent,
                 enrichedPage.getSource()
                             .get(PAGEDATA.getIndexDocProperty()));
  }

  @Test
  public void testEnrichPageUpdateResourceDataPayload() throws IOException {
    Page page = new Page();

    UpdateResourcePayload payload = new UpdateResourcePayload();
    payload.setPage(page);
    Map<String, Object> expectedDownloadMap = new HashMap<>();
    expectedDownloadMap.put(FILE.getIndexDocProperty(), "testFileLocation");
    expectedDownloadMap.put(CONTENT.getIndexDocProperty(), "testContent");

    payload.setContent(expectedDownloadMap);


    Page enrichedPage = service.enrichPage(payload);
    assertEquals(Lists.newArrayList(expectedDownloadMap),
                 enrichedPage.getSource()
                             .get(DOWNLOADS.getIndexDocProperty()));
  }
}