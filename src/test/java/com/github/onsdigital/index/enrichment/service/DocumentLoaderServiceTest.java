package com.github.onsdigital.index.enrichment.service;

import com.beust.jcommander.internal.Lists;
import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.model.Page;
import com.github.onsdigital.index.enrichment.model.payload.UpdatePageDataPayload;
import com.github.onsdigital.index.enrichment.model.payload.UpdateResourcePayload;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Test
 */

@RunWith(MockitoJUnitRunner.class)
public class DocumentLoaderServiceTest {

  @Mock ElasticRepository repo;

  private DocumentLoaderService service;


  @Before
  public void init() {
    service = new DocumentLoaderService();
    service.setRepository(repo);


  }

  @Test
  public void loadJsonPage() throws Exception {


    String testURI = "testUri";
    List<String> testJson = Lists.newArrayList("testJson");
    String index = "ons";

    UpdatePageDataPayload payload = new UpdatePageDataPayload().setUri(testURI)
                                                           .setContent(testJson);

    Page foundPage = new Page();
    when(repo.loadPage(eq(testURI), eq(index))).thenReturn(foundPage);

    UpdatePageDataPayload actualPayload = service.load(payload);

    assertEquals(foundPage, actualPayload.getPage());
    assertEquals(testJson, actualPayload.getContent());
    Mockito.verify(repo)
           .loadPage(testURI, index);


  }


  @Test
  public void loadResourcePage() throws Exception {
    String testURI = "testUri";
    Map<String,Object> testContent = new HashMap<>();
    String index = "ons";

    UpdateResourcePayload request = new UpdateResourcePayload().setUri(testURI)
                                                               .setContent(testContent);


    Page foundPage = new Page();
    when(repo.loadPage(eq(testURI), eq(index))).thenReturn(foundPage);

    UpdateResourcePayload actualPayload = service.load(request);

    assertEquals(foundPage, actualPayload.getPage());
    assertEquals(testContent, actualPayload.getContent());
    Mockito.verify(repo)
           .loadPage(testURI, index);


  }


}