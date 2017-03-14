package com.github.onsdigital.index.enrichment.elastic.api;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * Created by guidof on 13/03/17.
 */
public class PayloadBuilderTest {

    @Test
    public void buildObject() throws Exception {
        PayloadBuilder<String, Object> plb = new PayloadBuilder();
        final Object v = new Object();
        plb.put("StringTestKey", v);
        assertEquals(v,
                     plb.build()
                        .get("StringTestKey"));
    }

    @Test
    public void buildString() throws Exception {
        PayloadBuilder<String, String> plb = new PayloadBuilder();
        plb.put("StringTestKey", "StringTestValue");
        assertEquals("StringTestValue",
                     plb.build()
                        .get("StringTestKey"));
    }

    @Test
    public void putNullValue() throws Exception {
        PayloadBuilder<String, String> plb = new PayloadBuilder();
        plb.put("StringTestKey", null);
        assertFalse(
                plb.build()
                   .keySet()
                   .stream()
                   .filter("StringTestKey"::equals)
                   .findFirst()
                   .isPresent());
    }
}