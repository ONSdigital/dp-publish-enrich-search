package com.github.onsdigital.index.enrichment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.model.Page;
import com.github.onsdigital.index.enrichment.service.util.ResourceUtils;
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

  public static Page buildData(final String id, final String index, final String type, final String file) {
    Page page = null;
    try {
        page = buildData(id, index, type, file, false);
    }
    catch (IOException e) {
      LOGGER.info("buildData([id, index, type, file]) : Should not reach this", e);
    }
    return page;
  }

  public static Page buildData(final String id, final String index, final String type,
                               final String file, boolean loadFile) throws IOException {

    Page page = new Page().setId(id)
                          .setIndex(index)
                          .setType(type)
                          .setDataFileLocation(file);

    if (loadFile) {
      String raw = ResourceUtils.readResourceToString(loader.getResource(file));
      page.setRaw(raw)
          .setSource(MAPPER.readValue(raw, Map.class));
    }

    return page;
  }
}
