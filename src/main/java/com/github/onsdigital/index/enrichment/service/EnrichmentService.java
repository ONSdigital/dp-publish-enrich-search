package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.model.Page;
import com.github.onsdigital.index.enrichment.model.payload.UpdatePageDataPayload;
import com.github.onsdigital.index.enrichment.model.payload.UpdateResourcePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

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
    public Page enrichPage(final UpdatePageDataPayload payload) throws IOException {

        Page page = payload.getPage();
        LOGGER.debug("enrichPage([UpdatePageDataPayload]) : Page {}", page
                .getId());
        page.getSource()
            .put(PAGEDATA.getIndexDocProperty(), payload.getContent());

        return page;
    }


    /**
     * Called by Spring Integration when a new Page and Content needs to be merged
     *
     * @param payload
     * @return
     */
    public Page enrichPage(final UpdateResourcePayload payload) throws IOException {

        final Page page = payload.getPage();
        LOGGER.debug("enrichPage([UpdatePageDataPayload]) : Page {}", page.getId());
        attachDownload(payload.getContent(), page);
        return page;
    }


}
