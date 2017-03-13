package com.github.onsdigital.index.enrichment.elastic.api.load;

import com.github.onsdigital.index.enrichment.elastic.api.Request;
import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import com.github.onsdigital.index.enrichment.exception.MissingRequirementsException;
import org.elasticsearch.client.RestClient;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * Created by guidof on 09/03/17.
 */
@RunWith(MockitoJUnitRunner.class)
public class LoadBuilderTest {

    @Mock
    RestClient elasticClient;

    @Test(expected = MissingRequirementsException.class)
    public void testLoadPageWithNoId() throws ElasticSearchException, IOException {
        final String expectedIndex = "testExpectedIndex";
        final String expectedTestType = "testedExpectedType";

        new LoadBuilder().setIndex(expectedIndex)
                         .setType(expectedTestType)
                         .setElasticClient(elasticClient)
                         .build();
    }

    @Test(expected = MissingRequirementsException.class)
    public void testLoadPageWithNoElasticClient() throws ElasticSearchException, IOException {
        final String expectedIndex = "testExpectedIndex";
        final String expectedTestType = "testedExpectedType";
        final String expectedTestId = "expectedTestId";

        new LoadBuilder().setIndex(expectedIndex)
                         .setType(expectedTestType)
                         .setId(expectedTestId)
                         .build();
    }

    @Test(expected = MissingRequirementsException.class)
    public void testLoadPageWithNoIndex() throws ElasticSearchException, IOException {

        final String expectedTestType = "testedExpectedType";
        final String expectedTestId = "expectedTestId";

        new LoadBuilder().setType(expectedTestType)
                         .setId(expectedTestId)
                         .setElasticClient(elasticClient)
                         .build();
    }

    @Test
    public void testLoadPage() throws ElasticSearchException, IOException {
        final String expectedIndex = "testExpectedIndex";
        final String expectedTestType = "testedExpectedType";
        final String expectedTestId = "expectedTestId";

        Request build = new LoadBuilder().setIndex(expectedIndex)
                                         .setType(expectedTestType)
                                         .setId(expectedTestId)
                                         .setElasticClient(elasticClient)
                                         .build();
        assertEquals("/testExpectedIndex/testedExpectedType/expectedTestId/", build.getEndpoint());
    }

}