package com.github.onsdigital.index.enrichment.exception;

/**
 * Thrown when the Elastic Search Website page can not be found
 */
public class ONSPageNotFoundException extends EnrichServiceException {
    public ONSPageNotFoundException() {
    }

    public ONSPageNotFoundException(final String message) {
        super(message);
    }

    public ONSPageNotFoundException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ONSPageNotFoundException(final Throwable cause) {
        super(cause);
    }

    public ONSPageNotFoundException(final String message, final Throwable cause, final boolean enableSuppression,
                                    final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
