package com.github.onsdigital.index.enrichment.service.analyse;

/**
 * Lucene Filters/TokenStream does not support streams, so using this FunctionalInterface to allow the client to determine how they want to collect the terms from the TokenStream
 * (i.e. they can capture as a StringBuffer (!?), StringBuilder, List or String[])
 *
 * @author James Fawke
 */
@FunctionalInterface
public interface KeyValueAccumulator {
  void put(String k, String v);
}
