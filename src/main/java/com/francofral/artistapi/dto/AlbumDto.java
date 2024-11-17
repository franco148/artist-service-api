package com.francofral.artistapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record AlbumDto (
    Long id,
    String title,
    @JsonProperty("year")
    Integer releaseYear
) {}
