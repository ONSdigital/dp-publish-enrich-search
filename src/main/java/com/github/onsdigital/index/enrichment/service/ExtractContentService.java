package com.github.onsdigital.index.enrichment.service;

import com.github.onsdigital.index.enrichment.exception.EnrichServiceException;
import com.github.onsdigital.index.enrichment.model.payload.UpdatePageDataPayload;
import com.github.onsdigital.index.enrichment.model.payload.UpdateResourcePayload;
import com.github.onsdigital.index.enrichment.model.request.EnrichPageRequest;
import com.github.onsdigital.index.enrichment.model.request.EnrichResourceRequest;
import com.github.onsdigital.index.enrichment.service.extract.ContentExtractor;
import com.github.onsdigital.index.enrichment.service.extract.ContentExtractorFactory;
import com.github.onsdigital.index.enrichment.service.util.JsonToStringConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.github.onsdigital.index.enrichment.model.ModelEnum.CONTENT;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.FILE;
import static com.github.onsdigital.index.enrichment.model.ModelEnum.UPDATED_AT;
import static com.github.onsdigital.index.enrichment.service.util.ResourceUtils.deriveUriFromJsonFileLocation;

/**
 * Extract the content from the file (with embedded Json or a S3/File: resoruce the contents is extracts, ready for adding to the page
 */
@Service
public class ExtractContentService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ExtractContentService.class);


    @Autowired
    private ContentExtractorFactory extractorFactory;

    public ContentExtractorFactory getExtractorFactory() {
        return extractorFactory;
    }

    public ExtractContentService setExtractorFactory(
            final ContentExtractorFactory extractorFactory) {
        this.extractorFactory = extractorFactory;
        return this;
    }

    public UpdateResourcePayload extract(EnrichResourceRequest request) throws EnrichServiceException {
        LOGGER.info("extract([EnrichResourceRequest]) : extracting downloadContent {} for page {}",
                    request.getS3Location(),
                    request.getFileLocation());

        return new UpdateResourcePayload().setContent(extractDownloadContent(request.getS3Location()))
                                          .setUri(request.getFileLocation())
                                          .setS3Location(request.getS3Location());
    }

    public UpdatePageDataPayload extract(EnrichPageRequest request) throws EnrichServiceException {

        String fileLocation = deriveUriFromJsonFileLocation(request.getFileLocation());
        LOGGER.info("load([EnrichPageRequest]) : extracting Json fpr page {}", fileLocation);

        List<String> content = new JsonToStringConverter(request.getFileContent()).extractText();
        return new UpdatePageDataPayload().setContent(content)
                                          .setUri(fileLocation);

    }

    private Map<String, Object> extractDownloadContent(final String dataJsonLocation) throws EnrichServiceException {
        Map<String, Object> content = new HashMap<>();
        content.put(FILE.getIndexDocProperty(), dataJsonLocation);
        content.put(CONTENT.getIndexDocProperty(), extractContent(dataJsonLocation, dataJsonLocation));
        content.put(UPDATED_AT.getIndexDocProperty(), new Date());
        return content;
    }

    private List<String> extractContent(final String dataJson, final String filePath) throws EnrichServiceException {
        ContentExtractor instance = getExtractorFactory().getInstance(dataJson, filePath);
        return instance.extract();
    }

}
