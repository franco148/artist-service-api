package com.francofral.artistapi.problem;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.stream.Collectors;

/**
 * A global exception handler for the Artist API. This class extends {@link ResponseEntityExceptionHandler}
 * and provides custom handling of specific exceptions related to the API.
 *
 * <p>This class is responsible for converting exceptions into structured error responses,
 * conforming to the {@link ErrorResponse} schema.</p>
 *
 * <p>Supported Exception Types:</p>
 * <ul>
 *   <li>{@link ArtistApiDomainException} - Custom domain exceptions annotated with {@link ExceptionHandlerSchema}.</li>
 *   <li>{@link MethodArgumentNotValidException} - Validation errors for request bodies.</li>
 * </ul>
 */
@Slf4j
@ControllerAdvice
public final class ArtistApiExceptionHandler extends ResponseEntityExceptionHandler {

    /**
     * Handles exceptions of type {@link ArtistApiDomainException}.
     *
     * <p>This method validates the presence of the {@link ExceptionHandlerSchema} annotation,
     * extracts the HTTP status and title information from the exception or its annotation, and
     * builds a structured {@link ErrorResponse} for the client.</p>
     *
     * @param exception The {@link ArtistApiDomainException} to handle.
     * @param request   The {@link WebRequest} associated with the exception.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with the appropriate HTTP status and details.
     */
    @ExceptionHandler(ArtistApiDomainException.class)
    public ResponseEntity<Problem> handleDomainException(ArtistApiDomainException exception, WebRequest request) {
        ExceptionHandlerSchema exceptionHandler = AnnotationUtils.findAnnotation(exception.getClass(), ExceptionHandlerSchema.class);

        validateException(exception, exceptionHandler);

        int status = getStatus(exception, exceptionHandler);
        String title = getTitle(exception, exceptionHandler);

        HttpServletRequest httpServletRequest = ((ServletWebRequest)request).getRequest();

        ErrorResponse errorResponse = ErrorResponse.builder()
                .title(title)
                .status(status)
                .detail(exception.getExceptionDetails())
                .path(httpServletRequest.getRequestURI())
                .build();

        return ResponseEntity.status(status).body(errorResponse);
    }

    /**
     * Handles validation errors caused by invalid method arguments.
     *
     * @param ex      The {@link MethodArgumentNotValidException} containing details of the validation error.
     * @param headers HTTP headers.
     * @param status  The HTTP status code.
     * @param request The {@link WebRequest} associated with the exception.
     * @return A {@link ResponseEntity} containing an {@link ErrorResponse} with details about the validation failure.
     */
    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers,
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {

        logger.error("A validation exception has occurred: " + ex.getBindingResult(), ex);

        HttpServletRequest httpServletRequest = ((ServletWebRequest)request).getRequest();

        String errorDetail = "";
        if (ex.hasFieldErrors()) {
            errorDetail = ex.getFieldErrors().stream()
                    .map(error -> String.format("[%s %s]", error.getField(), error.getDefaultMessage()))
                    .collect(Collectors.joining(", "));
        }

        ErrorResponse errorResponse = ErrorResponse.builder()
                .title("Validations for the entity have not passed.")
                .status(HttpStatus.BAD_REQUEST.value())
                .detail(errorDetail)
                .path(httpServletRequest.getRequestURI())
                .build();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
    }

    /**
     * Validates the presence and correctness of the {@link ExceptionHandlerSchema} annotation on the exception class.
     *
     * @param exception       The {@link ArtistApiDomainException} to validate.
     * @param exceptionHandler The {@link ExceptionHandlerSchema} annotation associated with the exception class.
     * @throws UnsupportedOperationException If the annotation is missing or contains invalid values.
     */
    private void validateException(ArtistApiDomainException exception, ExceptionHandlerSchema exceptionHandler) {
        if (!(exception instanceof DefaultDomainException) && null == exceptionHandler) {
            log.error("The problem class does not have @ExceptionHandlerSchema annotation.");

            String errorFormat = "The annotation @ExceptionHandlerSchema is required in problem class: '%s'";
            throw new UnsupportedOperationException(String.format(errorFormat, exception.getClass()));
        }

        if (!(exception instanceof DefaultDomainException) && !StringUtils.hasText(exceptionHandler.title())) {

            log.error("The @ExceptionHandlerSchema annotation does not contain problem title.");

            throw new UnsupportedOperationException("The title field in @ExceptionHandlerSchema annotation cannot be empty");
        }
    }

    /**
     * Extracts the HTTP status code from the {@link ArtistApiDomainException} or its {@link ExceptionHandlerSchema} annotation.
     *
     * @param exception       The {@link ArtistApiDomainException} to extract the status from.
     * @param exceptionHandler The {@link ExceptionHandlerSchema} annotation associated with the exception class.
     * @return The HTTP status code to be returned in the response.
     */
    private int getStatus(ArtistApiDomainException exception, ExceptionHandlerSchema exceptionHandler) {
        int response;

        if (exception instanceof DefaultDomainException) {
            response = ((DefaultDomainException) exception).getStatus().value();
        } else {
            response = exceptionHandler.code().value();
        }

        return response;
    }

    /**
     * Extracts the title of the error from the {@link ArtistApiDomainException} or its {@link ExceptionHandlerSchema} annotation.
     *
     * @param exception       The {@link ArtistApiDomainException} to extract the title from.
     * @param exceptionHandler The {@link ExceptionHandlerSchema} annotation associated with the exception class.
     * @return The title of the error to be returned in the response.
     */
    private String getTitle(ArtistApiDomainException exception, ExceptionHandlerSchema exceptionHandler) {
        String titleResponse;

        if (exception instanceof DefaultDomainException) {
            titleResponse = DefaultDomainException.TITLE;
        } else {
            titleResponse = exceptionHandler.title();
        }

        return titleResponse;
    }
}
