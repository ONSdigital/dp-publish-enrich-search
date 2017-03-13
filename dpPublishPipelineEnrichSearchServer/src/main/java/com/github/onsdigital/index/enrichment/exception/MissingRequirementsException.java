package com.github.onsdigital.index.enrichment.exception;

/**
 * Created by guidof on 09/03/17.
 */
public class MissingRequirementsException extends ElasticSearchException {
    public MissingRequirementsException() {
    }

    public MissingRequirementsException(final String message) {
        super(message);
    }

    public MissingRequirementsException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public MissingRequirementsException(final Throwable cause) {
        super(cause);
    }

    public MissingRequirementsException(final String message, final Throwable cause, final boolean enableSuppression,
                                        final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
