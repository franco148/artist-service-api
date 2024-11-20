package com.francofral.artistapi.problem;

import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.ServletWebRequest;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class ArtistApiExceptionHandlerTest {

    @Test
    void shouldHandleDomainException() {

        ArtistApiDomainException exception = mock(EntityNotFoundException.class);
        ServletWebRequest webRequest = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(webRequest.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getRequestURI()).thenReturn("/example");
        when(exception.getExceptionDetails()).thenReturn("Something went wrong");

        ArtistApiExceptionHandler handler = new ArtistApiExceptionHandler();


        ResponseEntity<Problem> response = handler.handleDomainException(exception, webRequest);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("/example", errorResponse.getPath());
        assertEquals("Something went wrong", errorResponse.getDetail());
    }

    @Test
    void shouldHandleValidationException() {

        MethodArgumentNotValidException exception = mock(MethodArgumentNotValidException.class);
        FieldError fieldError = new FieldError("objectName", "fieldName", "Invalid value");
        when(exception.hasFieldErrors()).thenReturn(true);
        when(exception.getFieldErrors()).thenReturn(Collections.singletonList(fieldError));

        ServletWebRequest webRequest = mock(ServletWebRequest.class);
        HttpServletRequest httpServletRequest = mock(HttpServletRequest.class);

        when(webRequest.getRequest()).thenReturn(httpServletRequest);
        when(httpServletRequest.getRequestURI()).thenReturn("/example");

        ArtistApiExceptionHandler handler = new ArtistApiExceptionHandler();


        ResponseEntity<Object> response = handler.handleMethodArgumentNotValid(
                exception, new HttpHeaders(), HttpStatus.BAD_REQUEST, webRequest
        );

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST.value(), response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() instanceof ErrorResponse);
        ErrorResponse errorResponse = (ErrorResponse) response.getBody();
        assertEquals("Validations for the entity have not passed.", errorResponse.getTitle());
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorResponse.getStatus());
        assertEquals("/example", errorResponse.getPath());
        assertEquals("[fieldName Invalid value]", errorResponse.getDetail());
    }

}