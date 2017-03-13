package com.github.onsdigital.index.enrichment.elastic.api;

import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

/**
 * Created by guidof on 08/03/17.
 */
public class EndpointBuilderTest {
    @Test
    public void getIndex() throws Exception {
        String index = "testIdex";
        assertEquals(index,
                     new EndpointBuilder().setIndex(index)
                                          .getIndex());
    }


    @Test
    public void getType() throws Exception {
        String type = "testType";
        assertEquals(type,
                     new EndpointBuilder().setType(type)
                                          .getType());
    }


    @Test
    public void getAction() throws Exception {
        Action action = Action.SEARCH;
        assertEquals(action,
                     new EndpointBuilder().setAction(action)
                                          .getAction());
    }

    @Test
    public void getId() throws Exception {
        String id = "testId";
        assertEquals(id,
                     new EndpointBuilder().setId(id)
                                          .getId());
    }

    @Test
    public void getIdEscaped() throws Exception {
        String id = "a/b/c";
        assertEquals("a%2Fb%2Fc",
                     new EndpointBuilder().setId(id)
                                          .getId());
    }

    @Test
    public void buildEmptySearch() throws Exception {
        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setAction(search)
                                               .build();

        assertEquals("/_search", endpoint);
    }

    @Test
    public void buildTypeOnlySearch() throws Exception {
        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setType("testType")
                                               .setAction(search)
                                               .build();
        assertEquals("/_search", endpoint);
    }


    @Test
    public void buildIdAndTypeOnlySearch() throws Exception {
        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setId("testId")
                                               .setType("testType")
                                               .setAction(search)
                                               .build();
        assertEquals("/_search", endpoint);
    }

    @Test
    public void buildIdAndTypeAndIdxSearch() throws Exception {
        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setId("testId")
                                               .setIndex("testIndex")
                                               .setType("testType")
                                               .setAction(search)
                                               .build();
        assertEquals("/testIndex/testType/testId/_search", endpoint);
    }

    @Test
    public void buildMissingTypeSearch() throws Exception {
        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setId("testId")
                                               .setIndex("testIndex")
                                               .setAction(search)
                                               .build();
        assertEquals("/testIndex/_search", endpoint);
    }

    @Test
    public void buildMissingAction() throws Exception {
        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setId("testId")
                                               .setIndex("testIndex")
                                               .setType("testType")
                                               .build();
        assertEquals("/testIndex/testType/testId/", endpoint);
    }

    @Test
    public void buildEmptyParameters() throws Exception {
        Map<String, String> parameter = new HashMap();
        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setId("testId")
                                               .setIndex("testIndex")
                                               .setType("testType")
                                               .setAction(Action.SEARCH)
                                               .setParameters(parameter)
                                               .build();
        assertEquals("/testIndex/testType/testId/_search", endpoint);
    }

    @Test
    public void buildParameter() throws Exception {
        Map<String, String> parameters = new HashMap();

        parameters.put("testParam1", "testParamValue1");

        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setId("testId")
                                               .setIndex("testIndex")
                                               .setType("testType")
                                               .setAction(Action.UPDATE)
                                               .setParameters(parameters)
                                               .build();
        assertEquals("/testIndex/testType/testId/_update?testParam1=testParamValue1", endpoint);
    }

    @Test
    public void buildParameters() throws Exception {
        Map<String, String> parameters = new HashMap();

        parameters.put("testParam1", "testParamValue1");
        parameters.put("testParam2", "testParamValue2");
        parameters.put("testParam3", "testParamValue3");
        Action search = Action.SEARCH;
        String endpoint = new EndpointBuilder().setId("testId")
                                               .setIndex("testIndex")
                                               .setType("testType")
                                               .setAction(Action.UPDATE)
                                               .setParameters(parameters)
                                               .build();
        assertEquals(
                "/testIndex/testType/testId/_update?testParam1=testParamValue1&amp;testParam2=testParamValue2&amp;testParam3=testParamValue3",
                endpoint);
    }
}