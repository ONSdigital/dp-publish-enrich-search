package com.github.onsdigital.index.enrichment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.model.Data;
import com.github.onsdigital.index.enrichment.service.analyse.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;
import java.util.Map;

/**
 * Helper to create test data objects
 */
public class DataFactory {
  private static final Logger LOGGER = LoggerFactory.getLogger(DataFactory.class);
  private static ObjectMapper MAPPER = new ObjectMapper();
  private static ResourceLoader loader = new DefaultResourceLoader();

  public static Data buildData(final String id, final String index, final String type, final String file) {
    Data data = null;
    try {
      data = buildData(id, index, type, file, false);
    }
    catch (IOException e) {
      LOGGER.info("buildData([id, index, type, file]) : Should not reach this", e);
    }
    return data;
  }

  public static Data buildData(final String id, final String index, final String type,
                               final String file, boolean loadFile) throws IOException {

    Data data = new Data().setId(id)
                          .setIndex(index)
                          .setType(type)
                          .setDataFileLocation(file);

    if (loadFile) {
      String raw = ResourceUtils.readFileToString(loader.getResource(file));
      data.setRaw(raw)
          .setSource(MAPPER.readValue(raw, Map.class));
    }

    return data;
  }
}
