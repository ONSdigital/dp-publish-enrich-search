package com.github.onsdigital.index.enrichment.elastic.api;

import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;

/**
 * Standard Interface defining the builders
 *
 */
public interface Builder<T> {
    T build() throws ElasticSearchException;
}
