package com.github.onsdigital.index.enrichment.model.request;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.onsdigital.index.enrichment.exception.EnrichServiceException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Created by fawks on 14/02/2017.
 */

public class PipelineRequestBuilder {

    private static final ObjectMapper MAPPER = new ObjectMapper();
    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineRequestBuilder.class);
    public static final String MISSING_PROPERTY = "Incorrect Request, expected '%1s' to be populated in Json :%2s";
    public static final String INCORRECT_CLASS = "Incorrect Mapping Type, expected '%1s' but got '%2s'";

    public static PipelineRequest buildRequests(final String json) throws EnrichServiceException {
        final PipelineRequest request;
        try {
            JsonNode jsonNode;
            jsonNode = MAPPER.readTree(json);

            if (null != jsonNode.findValue("s3Location")) {
                LOGGER.info("buildRequests([json]) : EnrichResourceRequest detected '{}' ", json);
                request = buildEnrichResourceRequest(json, jsonNode);
            }
            else if (null != jsonNode.findValue("fileContent")) {
                LOGGER.info("buildRequests([json]) : EnrichPageRequest  detected '{}' ", json);
                request = buildEnrichPageRequest(json, jsonNode);
            }
            else {
                throw new EnrichServiceException("Failed to resolve request type for json " + json);
            }
        }
        catch (IOException e) {
            LOGGER.warn("transform([json]) : ", e.getMessage(), e);
            throw new EnrichServiceException("Failed to build request for '" + json + "' because " + e.getMessage(), e);
        }
        return request;
    }

    private static PipelineRequest buildEnrichResourceRequest(final String json,
                                                              final JsonNode jsonNode) throws com.fasterxml.jackson.core.JsonProcessingException, EnrichServiceException {
        final PipelineRequest request = MAPPER.treeToValue(jsonNode, EnrichResourceRequest.class);
        if (!(request instanceof EnrichResourceRequest)) {
            throw new EnrichServiceException(String.format(INCORRECT_CLASS,
                                                           EnrichResourceRequest.class.getSimpleName(),
                                                           request.getClass()
                                                                  .getSimpleName()));
        }

        EnrichResourceRequest enrichResourceRequest = (EnrichResourceRequest) request;

        if (StringUtils.isBlank(enrichResourceRequest.getS3Location())) {
            throw new EnrichServiceException(String.format(MISSING_PROPERTY, "S3Location property", json));
        }
        if (StringUtils.isBlank(enrichResourceRequest.getFileLocation())) {
             throw new EnrichServiceException(String.format(MISSING_PROPERTY, "FileLocation property", json));
        }

        return request;
    }

    private static PipelineRequest buildEnrichPageRequest(final String json,
                                                          final JsonNode jsonNode) throws com.fasterxml.jackson.core.JsonProcessingException, EnrichServiceException {
        final PipelineRequest request = MAPPER.treeToValue(jsonNode, EnrichPageRequest.class);

        if (!(request instanceof EnrichPageRequest)) {
            throw new EnrichServiceException(String.format(INCORRECT_CLASS,
                                                           EnrichPageRequest.class.getSimpleName(),
                                                           request.getClass()
                                                                  .getSimpleName()));
        }

        EnrichPageRequest resourceRequest = (EnrichPageRequest) request;

        if (StringUtils.isBlank(resourceRequest.getFileContent())) {
            throw new EnrichServiceException(String.format(MISSING_PROPERTY, "FileContent property", json));
        }

        if (StringUtils.isBlank(resourceRequest.getFileLocation())) {
            throw new EnrichServiceException(String.format(MISSING_PROPERTY, "FileLocation property", json));
        }

        return request;
    }
}
