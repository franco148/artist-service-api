package com.francofral.artistapi.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AlbumDtoWrapper(

    @JsonProperty("releases")
    List<AlbumDto> albums
) {}
