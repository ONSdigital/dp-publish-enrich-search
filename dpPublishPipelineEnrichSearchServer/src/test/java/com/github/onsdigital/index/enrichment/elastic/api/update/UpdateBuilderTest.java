package com.github.onsdigital.index.enrichment.elastic.api.update;

import com.github.onsdigital.index.enrichment.elastic.api.Action;
import com.github.onsdigital.index.enrichment.elastic.api.Request;
import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import com.github.onsdigital.index.enrichment.exception.MissingRequirementsException;
import com.jayway.jsonpath.JsonPath;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.github.onsdigital.index.enrichment.elastic.util.ElasticTestUtils.extractString;
import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class UpdateBuilderTest {

    @Mock
    RestClient elasticClient;

    @Test
    public void getVersion() throws Exception {
        final Long expected = 666L;
        assertEquals(expected,
                     new UpdateBuilder().setVersion(expected)
                                        .getVersion());
    }

    @Test
    public void getAction() throws Exception {
        final Action expected = Action.UPDATE;
        assertEquals(expected, new UpdateBuilder().getAction());
    }

    ;

    @Test
    public void getDoc() throws Exception {
        final Map<String, Object> expected = new HashMap();
        assertEquals(expected,
                     new UpdateBuilder().setDoc(expected)
                                        .getDoc());
    }

    @Test
    public void getUpsert() throws Exception {
        final Map<String, Object> expected = new HashMap();
        assertEquals(expected,
                     new UpdateBuilder().setUpsert(expected)
                                        .getUpsert());
    }


    @Test(expected = MissingRequirementsException.class)
    public void testUpdatePageMissingDocument() throws ElasticSearchException, IOException {
        final String expectedIndex = "testExpectedIndex";
        final String expectedTestType = "testedExpectedType";
        final String expectedTestId = "expectedTestId";

        new UpdateBuilder().setIndex(expectedIndex)
                           .setType(expectedTestType)
                           .setId(expectedTestId)
                           .setElasticClient(elasticClient)
                           .build();
    }


    @Test(expected = MissingRequirementsException.class)
    public void testUpdatePageEmptyDocument() throws ElasticSearchException, IOException {
        final Map<String, Object> expectedDoc = new HashMap();
        final String expectedIndex = "testExpectedIndex";
        final String expectedTestType = "testedExpectedType";
        final String expectedTestId = "expectedTestId";

        new UpdateBuilder().setIndex(expectedIndex)
                           .setType(expectedTestType)
                           .setId(expectedTestId)
                           .setElasticClient(elasticClient)
                           .setDoc(expectedDoc)
                           .build();
    }

    @Test
    public void testUpdatePage() throws ElasticSearchException, IOException {
        final Map<String, Object> expectedDoc = new HashMap();
        expectedDoc.put("root", "rootValue");
        final String expectedIndex = "testExpectedIndex";
        final String expectedTestType = "testedExpectedType";
        final String expectedTestId = "expectedTestId";

        final Request build = new UpdateBuilder().setIndex(expectedIndex)
                                                 .setType(expectedTestType)
                                                 .setId(expectedTestId)
                                                 .setElasticClient(elasticClient)
                                                 .setDoc(expectedDoc)
                                                 .build();

        String json = extractString(build);

        assertEquals("rootValue", JsonPath.<String>read(json, "$.doc.root"));

    }

    @Test
    public void testUpsertPage() throws ElasticSearchException, IOException {
        final Map<String, Object> expectedDoc = new HashMap();
        expectedDoc.put("root", "rootValue");

        final Map<String, Object> expectedUpsertDoc = new HashMap();
        expectedUpsertDoc.put("newRoot", "newRootValue");

        final String expectedIndex = "testExpectedIndex";
        final String expectedTestType = "testedExpectedType";
        final String expectedTestId = "expectedTestId";

        final Request build = new UpdateBuilder().setIndex(expectedIndex)
                                                 .setType(expectedTestType)
                                                 .setId(expectedTestId)
                                                 .setElasticClient(elasticClient)
                                                 .setDoc(expectedDoc)
                                                 .setUpsert(expectedUpsertDoc)
                                                 .build();

        String json = extractString(build);

        assertEquals("rootValue", JsonPath.<String>read(json, "$.doc.root"));
        assertEquals("newRootValue", JsonPath.<String>read(json, "$.upsert.newRoot"));

    }
}