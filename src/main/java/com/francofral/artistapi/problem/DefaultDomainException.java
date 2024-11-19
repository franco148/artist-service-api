package com.francofral.artistapi.problem;

import org.springframework.http.HttpStatus;

public final class DefaultDomainException extends ArtistApiDomainException {

    public static final String TITLE = "The process cannot continue";

    private final String errorMessage;
    private final HttpStatus status;

    public DefaultDomainException(HttpStatus status, String message) {
        this(status, message, null);
    }

    public DefaultDomainException(HttpStatus status, String message, Throwable cause) {
        super(message, cause);

        this.status = status;
        this.errorMessage = message;
    }

    protected HttpStatus getStatus() {
        return status;
    }

    @Override
    protected String getExceptionDetails() {
        return errorMessage;
    }
}
