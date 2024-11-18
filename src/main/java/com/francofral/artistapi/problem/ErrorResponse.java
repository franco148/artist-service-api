package com.francofral.artistapi.problem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Schema(name = "ErrorResponse", description = "Errors model information")
@Builder
@Getter
public final class ErrorResponse implements Problem {

    private final String title;
    private final int status;
    private final String detail;
    private final String path;
}
