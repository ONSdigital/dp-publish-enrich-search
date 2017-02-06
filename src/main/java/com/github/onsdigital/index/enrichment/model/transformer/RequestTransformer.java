package com.github.onsdigital.index.enrichment.model.transformer;

import com.github.onsdigital.index.enrichment.model.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.transformer.GenericTransformer;

import java.io.IOException;

/**
 * Request and its subtype Spring Transformer
 */
public class RequestTransformer implements GenericTransformer<byte[], Request> {
  private static final Logger LOGGER = LoggerFactory.getLogger(RequestTransformer.class);

  private static final SubTypeMapper<Request> MAPPER = new SubTypeMapper<>(Request.class);

  @Override
  public Request transform(final byte[] source) {
    Request request = null;
    try {
      request = MAPPER.readValue(source);
    }
    catch (IOException e) {
      LOGGER.error("transform([source]) : error parsing {} ", new String(source));
    }
    return request;
  }
}
