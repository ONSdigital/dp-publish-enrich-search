package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import com.github.onsdigital.index.enrichment.exception.EnrichServiceException;
import com.github.onsdigital.index.enrichment.model.Document;
import com.github.onsdigital.index.enrichment.model.payload.UpdatePageDataPayload;
import com.github.onsdigital.index.enrichment.model.payload.UpdateResourcePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Loads the document from the data store for enrichment
 **/


@Service
public class DocumentLoaderService {


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

    public UpdateResourcePayload load(UpdateResourcePayload request) throws EnrichServiceException, ElasticSearchException {
        String uri = request.getUri();
        LOGGER.debug("load([UpdateResourcePayload]) : loading  {}", uri);

        Document document = this.loadPage(uri);
        return request.setPage(document);

    }

    public UpdatePageDataPayload load(UpdatePageDataPayload request) throws EnrichServiceException, ElasticSearchException {
        String uri = request.getUri();
        LOGGER.debug("load([UpdatePageDataPayload]) : loading  {}", uri);

        return request.setPage(this.loadPage(uri));

    }


    private Document loadPage(String uri) throws EnrichServiceException, ElasticSearchException {

        return getRepository().loadPage(uri, INDEX_ALIAS);
    }


}
