package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.model.Data;
import com.github.onsdigital.index.enrichment.model.ModelEnum;
import com.github.onsdigital.index.enrichment.service.extract.ContentExtractorFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.DefaultResourceLoader;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by fawks on 06/02/2017.
 */
public class EnrichmentServiceTest {

  EnrichmentService service = new EnrichmentService();

  @Before
  public void init() {
    ContentExtractorFactory factory = new ContentExtractorFactory().setRootFolder("src/test/resources")
                                                                   .setResourceLoader(new DefaultResourceLoader());
    service.setExtractorFactory(factory);
  }

  @Test
  public void testEnrichDocument() throws IOException {
    Data data = DataFactory.buildData("1", "testIndex", "testType", "classpath:data.json", true);
    Data enrichedData = service.enrichDocument(data);
    List<Map> downloads = (List<Map>) enrichedData.getSource()
                                                  .get(ModelEnum.DOWNLOADS.getIndexDocProperty());

    assertNotNull(downloads);
    assertEquals(2, downloads.size());

    Object pageData = enrichedData.getSource()
                                  .get(ModelEnum.PAGEDATA.getIndexDocProperty());
    assertNotNull(pageData);


  }

}