package com.francofral.artistapi.problem;

public abstract class ArtistApiDomainException extends RuntimeException {

    protected ArtistApiDomainException(Throwable cause) {
        super(cause);
    }

    protected abstract String getExceptionDetails();
}
