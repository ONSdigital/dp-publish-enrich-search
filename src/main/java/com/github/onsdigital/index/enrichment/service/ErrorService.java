package com.github.onsdigital.index.enrichment.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.MessageHandlingException;
import org.springframework.stereotype.Service;

/**
 * Just log and move on at the moment
 */
@Service
public class ErrorService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ErrorService.class);

    public void error(Object obj) {
        //Just Log at the moment
        LOGGER.warn("error([obj]) : Error Class {}", obj.getClass());
        LOGGER.warn("error([obj]) : Error received {}", obj);

    }

    public void error(MessageHandlingException obj) {
        //Just Log at the moment
        LOGGER.warn("error([MessageHandlingException]) : Error Class {}", obj.getClass());
        LOGGER.warn("error([MessageHandlingException]) : Error received {}", obj.getMessage(), obj);

    }


}
