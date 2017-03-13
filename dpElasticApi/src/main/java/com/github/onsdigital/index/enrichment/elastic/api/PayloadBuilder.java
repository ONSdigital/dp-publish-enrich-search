package com.github.onsdigital.index.enrichment.elastic.api;

import java.util.HashMap;
import java.util.Map;

/**
 * Payload Builder that checks that the Key and the Value are not null when adding to the map to ensure that
 * non-requested parameters are not added to the payload with null params.
 */
public class PayloadBuilder<K, V> {
    Map<K, V> payload = new HashMap<>();

    public void put(K k, V v) {
        if (null != k && null != v) {
            payload.put(k, v);
        }
    }

    public Map<K, V> build() {
        return payload;
    }
}
