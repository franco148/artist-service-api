package com.francofral.artistapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "AlbumDtoWrapper", description = "DTO that represents part of the information from discogs API's albums")
public record AlbumDtoWrapper(
    @Schema(
        name = "albums",
        description = "List of albums produced by the artist of band"
    )
    @JsonProperty("releases")
    List<AlbumDto> albums
) {}
