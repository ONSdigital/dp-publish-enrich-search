package com.github.onsdigital.index.enrichment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.exception.EnrichServiceException;
import com.github.onsdigital.index.enrichment.model.Page;
import com.github.onsdigital.index.enrichment.model.payload.UpdatePageDataPayload;
import com.github.onsdigital.index.enrichment.model.payload.UpdateResourcePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Loads the document from the datastore for enrichment
 **/


@Service
public class DocumentLoaderService {

    public final static ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(DocumentLoaderService.class);
    private static final String INDEX_ALIAS = "ons";

    public ElasticRepository getRepository() {
        return repository;
    }

    public DocumentLoaderService setRepository(
            final ElasticRepository repository) {
        this.repository = repository;
        return this;
    }

    @Autowired
    private ElasticRepository repository;



    /**
     * Called by Spring Integration when a new EnrichResourceRequest is provided.
     * This class will load the resource from the S3 location and then add to the index document
     *
     * @param request a request that holds a reference to a S3 'Resource' as understood by Spring
     * @return
     */

    public UpdateResourcePayload load(UpdateResourcePayload request) throws EnrichServiceException {
        String uri = request.getUri();
        LOGGER.info("load([UpdateResourcePayload]) : loading  {}", uri);

        Page page = this.loadPage(uri);
        return request.setPage(page);

    }

    public UpdatePageDataPayload load(UpdatePageDataPayload request) throws EnrichServiceException {
        String uri = request.getUri();
        LOGGER.info("load([UpdatePageDataPayload]) : loading  {}", uri);

        return request.setPage(this.loadPage(uri));

    }


    private Page loadPage(String uri) throws EnrichServiceException {
        return getRepository().loadPage(uri, INDEX_ALIAS);
    }


}
