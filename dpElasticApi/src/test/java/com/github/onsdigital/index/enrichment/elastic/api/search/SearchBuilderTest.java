package com.github.onsdigital.index.enrichment.elastic.api.search;

import com.github.onsdigital.index.enrichment.elastic.api.Request;
import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import com.github.onsdigital.index.enrichment.exception.NotSupportedException;
import com.google.common.collect.Lists;
import com.jayway.jsonpath.JsonPath;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.github.onsdigital.index.enrichment.elastic.api.ElasticParameters.STORED_FIELDS;
import static com.github.onsdigital.index.enrichment.elastic.util.ElasticTestUtils.extractString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by guidof on 08/03/17.
 */

@RunWith(MockitoJUnitRunner.class)
public class SearchBuilderTest {

    @Mock RestClient elasticClient;

    @Test
    public void testSearchIndexEndpoint() throws ElasticSearchException, IOException {
        final String expectedIndex = "testExpectedIndex";

        Request build = new SearchBuilder().setIndex(expectedIndex)
                                           .setElasticClient(elasticClient)
                                           .build();
        assertEquals("/" + expectedIndex + "/_search", build.getEndpoint());

    }

    @Test(expected = NotSupportedException.class)
    public void testSearchIndexIdEndpoint() throws ElasticSearchException, IOException {
        final String expectedIndex = "testExpectedIndex";
        final String expectedTestId = "testedExpectedId";
        Request build = new SearchBuilder().setIndex(expectedIndex)
                                           .setId(expectedTestId)
                                           .build();


    }


    @Test
    public void testSearchIndexTypeIdEndpoint() throws ElasticSearchException, IOException {
        final String expectedIndex = "testExpectedIndex";
        final String expectedTestType = "testedExpectedType";

        Request build = new SearchBuilder().setIndex(expectedIndex)
                                           .setType(expectedTestType)
                                           .setElasticClient(elasticClient)
                                           .build();

        assertEquals("/" + expectedIndex + "/" + expectedTestType + "/_search", build.getEndpoint());

    }


    @Test
    public void testTermQuery() throws ElasticSearchException, IOException {
        final String expectedTermValue = "expectedTermValue";
        final String expectedStoredFieldValue = "expectedStoredFieldValue";
        final String expectedStoredFieldValue2 = "expectedStoredFieldValue2";

        TermQuery query = new TermQuery("_id",
                                        new TermQueryOptions().setValue(expectedTermValue)
                                                              .setBoost(66f));
        final Integer expectedSize = 10;
        final Integer expectedFrom = 20;

        Request build = new SearchBuilder().setQuery(query)
                                           .addStoredField(expectedStoredFieldValue)
                                           .addStoredField(expectedStoredFieldValue2)
                                           .setSize(expectedSize)
                                           .setFrom(expectedFrom)
                                           .setElasticClient(elasticClient)
                                           .build();

        String json = extractString(build);

        assertEquals(Lists.newArrayList(expectedStoredFieldValue, expectedStoredFieldValue2),
                     JsonPath.<List<String>>read(json, "$.stored_fields"));
        assertEquals(expectedTermValue, JsonPath.<String>read(json, "$.query.term._id.value"));
        assertEquals(expectedSize, JsonPath.<Integer>read(json, "$.size"));
        assertEquals(expectedFrom, JsonPath.<Integer>read(json, "$.from"));

    }

    @Test
    public void testNullStoredFieldsNotAddedToMap() {
        Map<String, Object> map = new SearchBuilder().buildPayload();
        assertFalse(map.keySet()
                       .stream()
                       .filter(s -> STORED_FIELDS.getText()
                                                 .equals(s))
                       .findFirst()
                       .isPresent());
    }

}