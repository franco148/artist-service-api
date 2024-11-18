package com.francofral.artistapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "AlbumDto", description = "DTO representing the info of Album entity")
public record AlbumDto (
    @Schema(
        name = "id",
        description = "The artist ID"
    )
    Long id,
    @Schema(
        name = "title",
        description = "The title of the album",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Worthy To Say"
    )
    String title,

    @Schema(
        name = "releaseYear",
        description = "The year in which the album was published",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "1999"
    )
    @JsonProperty("year")
    Integer releaseYear
) {}
