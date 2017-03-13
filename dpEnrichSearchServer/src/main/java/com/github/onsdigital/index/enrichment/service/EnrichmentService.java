package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.model.Document;
import com.github.onsdigital.index.enrichment.model.payload.UpdatePageDataPayload;
import com.github.onsdigital.index.enrichment.model.payload.UpdateResourcePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import static com.github.onsdigital.index.enrichment.model.ModelEnum.PAGEDATA;
import static com.github.onsdigital.index.enrichment.service.util.PageUtils.attachDownload;

/**
 * Enrichment Service that takes a indexed Page and the relevant content and combined the two into an updated
 *
 * @author James Fawke
 * @since 0.0.1
 */
@Service
public class EnrichmentService {


    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichmentService.class);


    /**
     * Called by Spring Integration when a new Page Data enrichment request is provided
     *
     * @param payload
     * @return
     */
    public Document enrichPage(final UpdatePageDataPayload payload)  {

        Document document = payload.getDocument();
        LOGGER.debug("enrichPage([UpdatePageDataPayload]) : Page {}", document
                .getId());
        document.getSource()
                .put(PAGEDATA.getIndexDocProperty(), payload.getContent());

        return document;
    }


    /**
     * Called by Spring Integration when a new Page and Content needs to be merged
     *
     * @param payload
     * @return
     */
    public Document enrichPage(final UpdateResourcePayload payload)  {

        final Document document = payload.getDocument();
        LOGGER.debug("enrichPage([UpdatePageDataPayload]) : Page {}", document.getId());
        attachDownload(payload.getContent(), document);
        return document;
    }


}
