package com.francofral.artistapi.client;

import com.francofral.artistapi.problem.BadRequestException;
import com.francofral.artistapi.problem.DefaultDomainException;
import com.francofral.artistapi.problem.ResourceNotFoundException;
import feign.Request;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class ArtistSearchErrorDecoderTest {

    @Test
    void shouldReturnBadRequestExceptionForStatus400() {

        ErrorDecoder defaultDecoder = mock(ErrorDecoder.class);
        ArtistSearchErrorDecoder decoder = new ArtistSearchErrorDecoder(defaultDecoder);
        Response response = createMockResponse(400, "Bad Request");

        Exception exception = decoder.decode("someMethodKey", response);

        assertTrue(exception instanceof BadRequestException);
        assertEquals("Bad Request", exception.getMessage());
    }

    @Test
    void shouldReturnResourceNotFoundExceptionForStatus404() {

        ErrorDecoder defaultDecoder = mock(ErrorDecoder.class);
        ArtistSearchErrorDecoder decoder = new ArtistSearchErrorDecoder(defaultDecoder);
        Response response = createMockResponse(404, "Not Found");

        Exception exception = decoder.decode("someMethodKey", response);

        assertTrue(exception instanceof ResourceNotFoundException);
        assertEquals("Not Found", exception.getMessage());
    }

    @Test
    void shouldReturnDefaultDomainExceptionForStatus500() {

        ErrorDecoder defaultDecoder = mock(ErrorDecoder.class);
        ArtistSearchErrorDecoder decoder = new ArtistSearchErrorDecoder(defaultDecoder);
        Response response = createMockResponse(500, "Internal Server Error");

        Exception exception = decoder.decode("someMethodKey", response);

        assertTrue(exception instanceof DefaultDomainException);
        assertEquals("Internal Server Error", exception.getMessage());
    }

    @Test
    void shouldDelegateToDefaultDecoderForOtherStatusCodes() {

        ErrorDecoder defaultDecoder = mock(ErrorDecoder.class);
        ArtistSearchErrorDecoder decoder = new ArtistSearchErrorDecoder(defaultDecoder);
        Response response = createMockResponse(418, "I'm a teapot");
        when(defaultDecoder.decode("someMethodKey", response))
                .thenReturn(new RuntimeException("Default decoder exception"));

        Exception exception = decoder.decode("someMethodKey", response);

        assertTrue(exception instanceof RuntimeException);
        assertEquals("Default decoder exception", exception.getMessage());
        verify(defaultDecoder).decode("someMethodKey", response);
    }

    // Helper method to create a mock Response object
    private Response createMockResponse(int status, String reason) {
        return Response.builder()
                .status(status)
                .reason(reason)
                .request(
                    Request.create(
                        Request.HttpMethod.GET,
                        "http://example.com",
                            Collections.emptyMap(),
                        null,
                        null,
                        null))
                .body("", StandardCharsets.UTF_8)
                .build();
    }
}