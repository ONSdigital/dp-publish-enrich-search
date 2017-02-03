package com.github.onsdigital.index.enrichment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.model.*;
import com.github.onsdigital.index.enrichment.service.analyse.util.ResourceUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.github.onsdigital.index.enrichment.model.ModelEnum.ID;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.TYPE;

/**
 * Loads the document from the datastore for enrichment
 **/


@Service
public class DocumentLoaderService {

  public final static ObjectMapper MAPPER = new ObjectMapper();
  private static final Logger LOGGER = LoggerFactory.getLogger(DocumentLoaderService.class);
  private static final String INDEX_ALIAS = "ons";

  @Autowired
  ResourceLoader resourceLoader;

  @Autowired
  private ElasticRepository repo;

  /**
   * Called by Spring Integration when a new Data URI is provided.
   * This class will load the class from the store (most probably Elastic)
   *
   * @param request A request that holds a list of document held in the elastic index
   * @return
   */
  public List<Data> loadDocuments(EnrichIndexedDocumentsRequest request) {
    LOGGER.info("loadDocument([obj]) : obj {}", request);

    return request.getDocuments()
                  .stream()
                  .map(d -> repo.loadData(d.getId(),
                                          d.getIndex(),
                                          d.getType()))
                  .collect(Collectors.toList());
  }

  /**
   * Called by Spring Integration when a new Data URI is provided.
   * This class will load the class from the store (most probably Elastic)
   *
   * @param request a request that holds a reference to a 'Resource' as understood by Spring
   * @return
   */
  public List<Data> loadResources(EnrichResourceDocumentsRequest request) {
    LOGGER.info("loadDocument([obj]) : obj {}", request);

    return request.getResources()
                  .stream()
                  .map(this::buildData)
                  .collect(Collectors.toList());
  }

  private Data buildData(ResourceDocument rDoc) {
    Resource r = resourceLoader.getResource(rDoc.getDataFileLocation());
    Data returnData = null;
    try {

      String dataJson  =  ResourceUtils.readFileToString(r);
      Map<String, Object> map = MAPPER.readValue(dataJson, Map.class);

      returnData = new Data().setIndex(INDEX_ALIAS)
                             .setId((String) map.get(ID.getFileProperty()))
                             .setType((String) map.get(TYPE.getFileProperty()))
                             .setDataFileLocation(rDoc.getDataFileLocation())
                             .setSource(map)
                             .setRaw(dataJson);
    }
    catch (IOException e) {
      LOGGER.error("buildData([r]) : Failed to process resource {}", r);
    }
    return returnData;
  }

  public Data[] loadAllDocuments(EnrichAllIndexedDocumentsRequest request) {
    List<Data> fullIndex = repo.listAllIndexDocuments(request.getIndex());
    LOGGER.info("loadAllDocuments([request]) :  number of Documents {}", fullIndex.size());
    return fullIndex.toArray(new Data[fullIndex.size()]);
  }

}
