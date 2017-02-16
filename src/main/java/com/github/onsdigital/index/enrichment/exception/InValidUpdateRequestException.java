package com.github.onsdigital.index.enrichment.exception;

/**
 * Created by fawks on 15/02/2017.
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
