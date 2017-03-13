package com.github.onsdigital.index.enrichment.exception;

/**
 * Thrown when a feture is not supported
 */
public class NotSupportedException extends RuntimeException {
    public NotSupportedException() {
    }

    public NotSupportedException(final String message) {
        super(message);
    }

    public NotSupportedException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public NotSupportedException(final Throwable cause) {
        super(cause);
    }

    public NotSupportedException(final String message, final Throwable cause, final boolean enableSuppression,
                                 final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
