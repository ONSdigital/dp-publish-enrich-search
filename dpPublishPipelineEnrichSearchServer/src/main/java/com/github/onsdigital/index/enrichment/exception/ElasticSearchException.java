package com.github.onsdigital.index.enrichment.exception;

/**
 * Created by guidof on 08/03/17.
 */
public class ElasticSearchException extends EnrichServiceException {


    public ElasticSearchException() {
    }

    public ElasticSearchException(final String message) {
        super(message);
    }

    public ElasticSearchException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ElasticSearchException(final Throwable cause) {
        super(cause);
    }

    public ElasticSearchException(final String message, final Throwable cause, final boolean enableSuppression,
                                  final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
