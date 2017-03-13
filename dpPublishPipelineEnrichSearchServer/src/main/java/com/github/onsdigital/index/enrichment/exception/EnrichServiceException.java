package com.github.onsdigital.index.enrichment.exception;

/**
 * Generic Exception thrown within The Enriching Flow
 */
public class EnrichServiceException extends Exception {
    public EnrichServiceException() {
    }

    public EnrichServiceException(final String message) {
        super(message);
    }

    public EnrichServiceException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public EnrichServiceException(final Throwable cause) {
        super(cause);
    }

    public EnrichServiceException(final String message, final Throwable cause, final boolean enableSuppression,
                                  final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
