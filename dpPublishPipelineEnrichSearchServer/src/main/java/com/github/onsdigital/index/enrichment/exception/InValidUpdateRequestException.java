package com.github.onsdigital.index.enrichment.exception;

/**
 * Thrown when the Elastic Search throws an invalid request exception, primaryly seen as part of the Optimistic locking failure
 */
public class InValidUpdateRequestException extends EnrichServiceException {
    public InValidUpdateRequestException() {
    }

    public InValidUpdateRequestException(final String message) {
        super(message);
    }

    public InValidUpdateRequestException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public InValidUpdateRequestException(final Throwable cause) {
        super(cause);
    }

    public InValidUpdateRequestException(final String message, final Throwable cause, final boolean enableSuppression,
                                         final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
