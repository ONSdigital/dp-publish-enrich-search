package com.github.onsdigital.index.enrichment.elastic;

import com.github.onsdigital.index.enrichment.model.Document;
import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.entity.ContentType;
import org.apache.http.nio.entity.NStringEntity;
import org.elasticsearch.client.Response;
import org.elasticsearch.client.RestClient;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Test.
 */
@RunWith(MockitoJUnitRunner.class)
public class ElasticRepositoryTest {


    @Mock
    private RestClient client;


    @Mock
    private Response response;


    private ElasticRepository repo = new ElasticRepository();

    @Before
    public void init() {
        repo.setElasticClient(client);
    }


    @Test
    public void loadData() throws Exception {
        final String type = "departments";
        final String index = "departments";
        final String id = "beis";

        when(client.performRequest(any(String.class),
                                   any(String.class),
                                   any(Map.class),
                                   any(HttpEntity.class))).thenReturn(response);
        String s = "{\"_index\":\"departments\",\"_type\":\"departments\",\"_id\":\"beis\",\"_version\":4,\"found\":true,\"_source\":{\"code\":\"beis\",\"name\":\"The Department for Business, Energy and Industrial Strategy\",\"url\":\"https://www.gov.uk/government/statistics?keywords=&topics%5B%5D=all&departments%5B%5D=department-for-business-energy-and-industrial-strategy&from_date=&to_date=\",\"terms\":[\"BEIS\",\"BIS\",\"business\",\"apprenticeship\",\"building\",\"construction\",\"trade union\",\"enterprise\",\"DECC\",\"energy\",\"solar\",\"coal\",\"oil\",\"gas\",\"electricity\",\"fuel poverty\",\"renewables\",\"greenhouse gas\"],\"aaa\":\"update3\"}}";
        when(response.getEntity()).thenReturn(new NStringEntity(s, ContentType.APPLICATION_JSON));


        final Document actual = repo.loadData(id, index, type);
        //Test Response is populated
        assertEquals(id, actual.getId());
        assertEquals(type, actual.getType());
        assertEquals(index, actual.getIndex());


    }

    @Test
    public void upsertData() throws Exception {
        final String type = "testType";
        final String index = "testIndex";
        final String id = "testId";
        final Map<String, Object> source = new HashMap<String, Object>();
        source.put("title", "testTitle");


        when(client.performRequest(any(String.class),
                                   any(String.class),
                                   any(Map.class),
                                   any(HttpEntity.class))).thenReturn(response);
        final StatusLine status = mock(StatusLine.class);
        when(response.getStatusLine()).thenReturn(status);
        when(status.getStatusCode()).thenReturn(200);

        repo.upsertData(id, index, type, source, 0L);
    }

}