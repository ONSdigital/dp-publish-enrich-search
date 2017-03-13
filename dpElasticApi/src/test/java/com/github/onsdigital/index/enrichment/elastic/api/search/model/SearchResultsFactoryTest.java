package com.github.onsdigital.index.enrichment.elastic.api.search.model;

import org.apache.commons.io.FileUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.when;

/**
 * Created by guidof on 08/03/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class SearchResultsFactoryTest {

    @Mock
    Response response;

    @Test
    public void testGetInstance() throws IOException {
        String s = FileUtils.readFileToString(
                new File(
                        "src/test/resources/com/github/onsdigital/index/enrichment/elastic/api/search/model/QueryResultsFactoryTestExample.json"));
        final NStringEntity nStringEntity = new NStringEntity(s, ContentType.APPLICATION_JSON);
        when(response.getEntity()).thenReturn(nStringEntity);
        final SearchResults instance = SearchResultsFactory.getInstance(response);
        assertNotNull(instance);
    }

}