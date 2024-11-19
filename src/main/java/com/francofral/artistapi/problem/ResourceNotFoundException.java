package com.francofral.artistapi.problem;

import org.springframework.http.HttpStatus;

@ExceptionHandlerSchema(code = HttpStatus.NOT_FOUND, title = ResourceNotFoundException.TITLE)
public final class ResourceNotFoundException extends ArtistApiDomainException {

    public static final String TITLE = "Resource could not be found";
    private static final String EXCEPTION_DETAIL_MESSAGE = "The request to the third-party API was not successful. %s";


    private final String message;

    public ResourceNotFoundException(String message) {
        this(message, null);
    }
    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    @Override
    protected String getExceptionDetails() {
        return String.format(EXCEPTION_DETAIL_MESSAGE, message);
    }
}
