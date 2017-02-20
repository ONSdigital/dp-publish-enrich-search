package com.github.onsdigital.index.enrichment.exception;

/**
 * Exception occurs when there is an issue with the extracting of content from a file or json
 */
public class ExtractContentException extends EnrichServiceException {
    public ExtractContentException() {
    }

    public ExtractContentException(final String message) {
        super(message);
    }

    public ExtractContentException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ExtractContentException(final Throwable cause) {
        super(cause);
    }

    public ExtractContentException(final String message, final Throwable cause, final boolean enableSuppression,
                                   final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
