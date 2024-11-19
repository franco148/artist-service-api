package com.francofral.artistapi.problem;

import org.springframework.http.HttpStatus;

@ExceptionHandlerSchema(code = HttpStatus.BAD_REQUEST, title = BadRequestException.TITLE)
public final class BadRequestException extends ArtistApiDomainException {

    public static final String TITLE = "Bad request";

    private final String message;

    public BadRequestException(String message) {
        this(message, null);
    }

    public BadRequestException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    protected String getExceptionDetails() {
        return message;
    }
}
