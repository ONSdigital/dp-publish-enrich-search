package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.model.Data;
import com.github.onsdigital.index.enrichment.service.analyse.util.JsonToStringConverter;
import com.github.onsdigital.index.enrichment.service.extract.ContentExtractorFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

import static com.github.onsdigital.index.enrichment.model.ModelEnum.CONTENT;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.DOWNLOADS;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.FILE;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.PAGEDATA;

/**
 * Enrichment Service that takes a document add the JSON document
 *
 * @author James Fawke
 * @since 0.0.1
 */
@Service
public class EnrichmentService {

    public static final String UPDATED_AT = "updatedAt";
    private static final Logger LOGGER = LoggerFactory.getLogger(EnrichmentService.class);
    @Autowired
    private ContentExtractorFactory extractorFactory;

    ContentExtractorFactory getExtractorFactory() {
        return extractorFactory;
    }

    EnrichmentService setExtractorFactory(final ContentExtractorFactory extractorFactory) {
        this.extractorFactory = extractorFactory;
        return this;
    }

    /**
     * Called by Spring Integration when a new Data is provided
     *
     * @param data
     * @return
     */
    public Data enrichDocument(final Data data) throws IOException {

        LOGGER.debug("enrichDocument([obj]) : obj {}", data.getId());


        Object downloads = data.getSource()
                               .get(DOWNLOADS.getFileProperty());


        //Rebuild Source so we only update the contents
        Map<String, Object> sourceUpdate = new HashMap();

        sourceUpdate.put(DOWNLOADS.getIndexDocProperty(), extractDownloads(data, downloads));
        sourceUpdate.put(PAGEDATA.getIndexDocProperty(), extractPageData(data.getRaw()));
        data.setSource(sourceUpdate);
        return data;
    }

    private List<String> extractPageData(final String raw) throws IOException {
        return new JsonToStringConverter(raw).extractText();

    }

    private List<Map<String, Object>> extractDownloads(final Data data, final Object downloads) {
        List<Map<String, Object>> downloadsUpdate = new ArrayList<>();
        if (downloads instanceof Collection) {
            ((Collection) downloads).forEach(obj -> {
                downloadsUpdate.add(updateContent(data.getDataFileLocation(), (Map) obj));
            });
        }
        return downloadsUpdate;
    }

    private Map updateContent(final String dataJsonLocation, final Map download) {

        final String file = (String) download.get(FILE.getFileProperty());
        download.put(CONTENT.getIndexDocProperty(), extractContent(dataJsonLocation, file));
        download.put(UPDATED_AT, new Date());
        return download;
    }


    private List<String> extractContent(final String dataJson, final String filePath) {
        return extractorFactory.getInstance(dataJson, filePath)
                               .extract();
    }

}
