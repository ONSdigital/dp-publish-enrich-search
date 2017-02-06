package com.github.onsdigital.index.enrichment.service;

import com.beust.jcommander.internal.Lists;
import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.model.*;
import com.github.onsdigital.index.enrichment.service.analyse.util.ResourceUtils;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.core.io.DefaultResourceLoader;

import java.util.List;

import static com.github.onsdigital.index.enrichment.service.DataFactory.buildData;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

/**
 * Created by fawks on 06/02/2017.
 */

@RunWith(MockitoJUnitRunner.class)
public class DocumentLoaderServiceTest {

  @Mock ElasticRepository repo;

  private DocumentLoaderService service;
  private final DefaultResourceLoader resourceLoader = new DefaultResourceLoader();

  @Before
  public void init() {
    service = new DocumentLoaderService();
    service.setRepo(repo);
    service.setResourceLoader(resourceLoader);

  }

  @Test
  public void loadDocuments() throws Exception {


    EnrichIndexedDocumentsRequest request = new EnrichIndexedDocumentsRequest();
    String testIndex = "testIndex";
    String testType = "testType";
    request.setDocuments(Lists.newArrayList(buildEnrichDocument
                                                (testIndex, "1", testType),
                                            buildEnrichDocument(testIndex, "2", testType)));


    Data file1 = buildData("1", testIndex, testType, "file1");
    when(repo.loadData(eq("1"), anyString(), anyString())).thenReturn(file1);

    Data file2 = buildData("2", testIndex, testType, "file2");


    when(repo.loadData(eq("2"), anyString(), anyString())).thenReturn(file2);

    List<Data> datas = service.loadDocuments(request);

    assertEquals(file1, datas.get(0));
    assertEquals(file2, datas.get(1));
    Mockito.verify(repo)
           .loadData("2", testIndex, testType);
    Mockito.verify(repo)
           .loadData("1", testIndex, testType);

  }

  private EnrichDocument buildEnrichDocument(final String testIndex, final String id, final String testType) {
    return new EnrichDocument().setIndex(testIndex)
                               .setId(id)
                               .setType(testType);
  }

  @Test
  public void loadResources() throws Exception {
    String dataFileLocation = "classpath:data.json";
    ResourceDocument file2 = new ResourceDocument().setDataFileLocation(dataFileLocation);

    List<ResourceDocument> resources = Lists.newArrayList(file2);
    EnrichResourceDocumentsRequest request = new EnrichResourceDocumentsRequest().setResources(resources);
    List<Data> datas = service.loadResources(request);

    assertNotNull(datas);

    String expected = ResourceUtils.readFileToString(resourceLoader.getResource(dataFileLocation));
    Data actual = datas.get(0);
    String raw = actual.getRaw();

    assertEquals(expected, raw);
    assertEquals("ons", actual.getIndex());
    assertEquals("testType", actual.getType());
    assertEquals("testUri", actual.getId());


  }

  @Test
  public void loadAllDocuments() throws Exception {
    String testIndex = "TestIndex";
    String testType = "TestType";
    EnrichAllIndexedDocumentsRequest request = new EnrichAllIndexedDocumentsRequest().setIndex("TestIndex");

    Data file1 = new Data().setId("1")
                           .setIndex(testIndex)
                           .setType(testType)
                           .setDataFileLocation("file1");

    when(repo.listAllIndexDocuments(eq("TestIndex"))).thenReturn(Lists.newArrayList(file1));

    Data[] datas = service.loadAllDocuments(request);
    assertEquals(1, datas.length);
    assertEquals(file1, datas[0]);

  }

}