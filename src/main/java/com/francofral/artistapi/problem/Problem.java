package com.francofral.artistapi.problem;

import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * @see <a href="https://tools.ietf.org/html/rfc7807">RFC 7807: Problem Details for HTTP APIs</a>
 */
public interface Problem {

    @Schema(name = "traceId", description = "The error trace identifier")
    default UUID getTraceId() {
        return UUID.randomUUID();
    }

    default LocalDateTime timestamp() { return LocalDateTime.now(); }

    @Schema(name = "title", description = "The error title")
    default String getTitle() {
        return null;
    }

    @Schema(name = "status", description = "The http error status")
    default int getStatus() {
        return 500;
    }

    @Schema(name = "detail", description = "The error details which contains the explanation of the error")
    default String getDetail() {
        return null;
    }

    @Schema(name = "path", description = "The path of the executed request")
    default String getPath() {
        return null;
    }
}
