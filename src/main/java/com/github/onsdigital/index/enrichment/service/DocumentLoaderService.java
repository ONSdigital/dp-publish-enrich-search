package com.github.onsdigital.index.enrichment.service;

import com.amazonaws.AmazonClientException;
import com.beust.jcommander.internal.Lists;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.model.*;
import com.github.onsdigital.index.enrichment.service.analyse.util.ResourceUtils;
import org.apache.commons.lang3.StringUtils;
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
  private ResourceLoader resourceLoader;

  @Autowired
  private ElasticRepository repo;

  ResourceLoader getResourceLoader() {
    return resourceLoader;
  }

  DocumentLoaderService setResourceLoader(final ResourceLoader resourceLoader) {
    this.resourceLoader = resourceLoader;
    return this;
  }

  ElasticRepository getRepo() {
    return repo;
  }

  DocumentLoaderService setRepo(final ElasticRepository repo) {
    this.repo = repo;
    return this;
  }

  /**
   * Called by Spring Integration when a new Data URI is provided.
   * This class will load the class from the store (most probably Elastic)
   *
   * @param request A request that holds a list of document held in the elastic index
   * @return
   */
  //TODO Remove once Pipeline interface is agreed
  public List<Data> loadDocuments(EnrichIndexedDocumentsRequest request) {
    LOGGER.debug("loadDocument([obj]) : obj {}", request);

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
  //TODO Remove once Pipeline interface is agreed
  public List<Data> loadResources(EnrichResourceDocumentsRequest request) {
    if (LOGGER.isDebugEnabled()) {

      LOGGER.debug("loadDocument([obj]) : loading  {}",
                  request.getResources()
                         .stream()
                         .map(r -> r.getDataFileLocation())
                         .collect(Collectors.joining(",\n")));

    }

    return request.getResources()
                  .stream()
                  .map(r -> r.getDataFileLocation())
                  .map(this::buildData)
                  .collect(Collectors.toList());
  }

  /**
   * Called by Spring Integration when a new PipelineRequest is provided.
   * This class will load the class from the S3 location
   *
   * @param request a request that holds a reference to a S3 'Resource' as understood by Spring
   * @return
   */

  public List<Data> loadPipelineDocument(PipelineRequest request) {
    String s3Location = request.getS3Location();
    if (LOGGER.isInfoEnabled()) {

      LOGGER.debug("loadPipelineDocument([obj]) : loading  {}", s3Location);

    }

    if (!StringUtils.startsWithIgnoreCase(s3Location,"s3:/")) {
      s3Location = ResourceUtils.concatenate("s3://", s3Location);
    }
    return Lists.newArrayList(this.buildData(s3Location));
  }

  private Data buildData(String dataFileLocation) {

    Resource r = resourceLoader.getResource(dataFileLocation);
    Data returnData = null;
    try {

      String dataJson = ResourceUtils.readFileToString(r);
      Map<String, Object> map = MAPPER.readValue(dataJson, Map.class);

      returnData = new Data().setIndex(INDEX_ALIAS)
                             .setId((String) map.get(ID.getFileProperty()))
                             .setType((String) map.get(TYPE.getFileProperty()))
                             .setDataFileLocation(dataFileLocation)
                             .setSource(map)
                             .setRaw(dataJson);
    }
    catch (IOException | AmazonClientException e) {
      LOGGER.error("buildData([r]) : Failed to process resource {}", dataFileLocation);
    }
    return returnData;
  }

  //TODO Remove once Pipeline interface is agreed
  public Data[] loadAllDocuments(EnrichAllIndexedDocumentsRequest request) {
    List<Data> fullIndex = repo.listAllIndexDocuments(request.getIndex());
    LOGGER.debug("loadAllDocuments([request]) :  number of Documents {}", fullIndex.size());
    return fullIndex.toArray(new Data[fullIndex.size()]);
  }

}
