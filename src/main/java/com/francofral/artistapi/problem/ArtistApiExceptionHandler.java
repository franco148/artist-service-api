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

@Slf4j
@ControllerAdvice
public final class ArtistApiExceptionHandler extends ResponseEntityExceptionHandler {

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

    private int getStatus(ArtistApiDomainException exception, ExceptionHandlerSchema exceptionHandler) {
        int response;

        if (exception instanceof DefaultDomainException) {
            response = ((DefaultDomainException) exception).getStatus().value();
        } else {
            response = exceptionHandler.code().value();
        }

        return response;
    }

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
