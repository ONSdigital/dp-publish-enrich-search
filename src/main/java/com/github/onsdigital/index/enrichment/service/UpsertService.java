package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.model.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * This service will upsert the document to ensure that the enriched version is updated into elastic store
 */
@Service
public class UpsertService {

  private static final Logger LOGGER = LoggerFactory.getLogger(UpsertService.class);

  @Autowired
  private ElasticRepository repo;

  /**
   * Called by Spring Integration when a new Data is and has been enriched.
   *
   * @param obj
   * @return
   */
  public void upsertDocument(Data obj) {
    LOGGER.debug("upsertDocument([obj]) : id {}", obj.getId());
    repo.upsertData(obj.getId(), obj.getIndex(), obj.getType(), obj.getSource());
  }
}
