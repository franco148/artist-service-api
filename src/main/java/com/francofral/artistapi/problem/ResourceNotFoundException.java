package com.francofral.artistapi.problem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;

@ExceptionHandlerSchema(code = HttpStatus.NOT_FOUND, title = ResourceNotFoundException.TITLE)
public final class ResourceNotFoundException extends ArtistApiDomainException {

    public static final String TITLE = "Resource could not be found";
    private static final String EXCEPTION_DETAIL_MESSAGE = "%s's request to %s was not satisfactory. The resource couldn't be found.";

    @Value("${artist.resource.api.name}")
    private String resourceApiName;

    private final String resourceName;

    public ResourceNotFoundException(String resourceName) {
        this(resourceName, null);
    }
    public ResourceNotFoundException(String resourceName, Throwable cause) {
        super(cause);
        this.resourceName = resourceName;
    }

    @Override
    protected String getExceptionDetails() {
        return String.format(EXCEPTION_DETAIL_MESSAGE, resourceName, resourceApiName);
    }
}
