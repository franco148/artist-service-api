package com.francofral.artistapi.problem;

import org.springframework.http.HttpStatus;

@ExceptionHandlerSchema(code = HttpStatus.NOT_FOUND, title = EntityNotFoundException.TITLE)
public final class EntityNotFoundException extends ArtistApiDomainException {

    public static final String TITLE = "Entity could not be found";

    private static final String EXCEPTION_DETAIL_MESSAGE = "%s with id %s could not be found or it does not exist.";

    private final Class<?> entityClass;
    private final Long entityId;

    public EntityNotFoundException(Class<?> entityClass, Long entityId) {
        this(entityClass, entityId, null);
    }

    public EntityNotFoundException(Class<?> entityClass, Long entityId, Throwable cause) {
        super(cause);

        this.entityClass = entityClass;
        this.entityId = entityId;
    }

    @Override
    protected String getExceptionDetails() {
        return String.format(EXCEPTION_DETAIL_MESSAGE, entityClass.getSimpleName(), entityId);
    }

    @Override
    public String getMessage() {
        return String.format(EXCEPTION_DETAIL_MESSAGE, entityClass.getSimpleName(), entityId);
    }
}
