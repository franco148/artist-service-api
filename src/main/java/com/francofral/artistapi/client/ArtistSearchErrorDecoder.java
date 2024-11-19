package com.francofral.artistapi.client;

import com.francofral.artistapi.problem.BadRequestException;
import com.francofral.artistapi.problem.DefaultDomainException;
import com.francofral.artistapi.problem.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.springframework.http.HttpStatus;

/**
 * A custom implementation of the {@link ErrorDecoder} interface for handling HTTP error responses in a Feign client.
 * This class maps specific HTTP status codes to custom exceptions, while delegating other cases to a fallback error decoder.
 *
 * <p>Behavior:</p>
 * <ul>
 *   <li>HTTP 400 - Throws {@link BadRequestException}</li>
 *   <li>HTTP 404 - Throws {@link ResourceNotFoundException}</li>
 *   <li>HTTP 500 - Throws {@link DefaultDomainException}</li>
 *   <li>Other statuses - Delegates to the provided fallback {@link ErrorDecoder}</li>
 * </ul>
 */
public final class ArtistSearchErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder;

    /**
     * Default constructor that initializes the error decoder with a default {@link feign.codec.ErrorDecoder.Default}.
     */
    public ArtistSearchErrorDecoder() {
        this(new Default());
    }

    /**
     * Constructor to initialize the error decoder with a custom fallback {@link ErrorDecoder}.
     *
     * @param errorDecoder The fallback error decoder to use for unhandled HTTP statuses.
     */
    public ArtistSearchErrorDecoder(ErrorDecoder errorDecoder) {
        this.errorDecoder = errorDecoder;
    }


    /**
     * Decodes an HTTP error response into a custom exception.
     *
     * @param methodKey The key representing the invoked Feign client method.
     * @param response  The HTTP response object containing the status, headers, and body.
     * @return An exception corresponding to the HTTP status code, or the result of the fallback error decoder.
     *
     * <ul>
     *   <li>HTTP 400 - Returns {@link BadRequestException} with the response reason.</li>
     *   <li>HTTP 404 - Returns {@link ResourceNotFoundException} with the response reason.</li>
     *   <li>HTTP 500 - Returns {@link DefaultDomainException} with the response reason and {@link HttpStatus#INTERNAL_SERVER_ERROR}.</li>
     *   <li>Other statuses - Delegates to the fallback {@link ErrorDecoder}.</li>
     * </ul>
     */
    @Override
    public Exception decode(String methodKey, Response response) {

        return switch (response.status()) {
            case 400 -> new BadRequestException(response.reason());
            case 404 -> new ResourceNotFoundException(response.reason());
            case 500 -> new DefaultDomainException(HttpStatus.INTERNAL_SERVER_ERROR, response.reason());
            default -> errorDecoder.decode(methodKey, response);
        };

    }
}
