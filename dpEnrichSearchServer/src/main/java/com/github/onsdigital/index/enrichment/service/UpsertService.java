package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.elastic.ElasticRepository;
import com.github.onsdigital.index.enrichment.exception.ElasticSearchException;
import com.github.onsdigital.index.enrichment.model.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

/**
 * This service will upsert the document to ensure that the enriched version is updated into elastic store
 */
@Service

public class UpsertService {
    private static final AtomicLong counter = new AtomicLong();
    private static final Logger LOGGER = LoggerFactory.getLogger(UpsertService.class);
    @Autowired
    private ElasticRepository repo;

    public ElasticRepository getRepo() {
        return repo;
    }

    public UpsertService setRepo(final ElasticRepository repo) {
        this.repo = repo;
        return this;
    }

    /**
     * Called by Spring Integration when a new Page is and has been enriched.
     *
     * @param obj
     * @return
     */
    public boolean upsert(Document obj) throws ElasticSearchException {
        LOGGER.info("upsertDocument([obj]) : #{} document updated with id {}", counter.incrementAndGet(), obj.getId());

        return getRepo().upsertData(obj.getId(), obj.getIndex(), obj.getType(), obj.getSource(), obj.getVersion());

    }
}
