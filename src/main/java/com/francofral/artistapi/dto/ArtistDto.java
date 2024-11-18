package com.francofral.artistapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "ArtistDto", description = "DTO representing the info of Artist entity")
public record ArtistDto(
    @Schema(
        name = "id",
        description = "The artist ID"
    )
    Long id,
    @Schema(
        name = "name",
        description = "The name of the artist of band",
        requiredMode = Schema.RequiredMode.REQUIRED,
        example = "Teddy Swims"
    )
    String name,
    @Schema(
        name = "profile",
        description = "A summary of the most relevant of the artist or band"
    )
    String profile,
    @Schema(
        name = "albums",
        description = "List of albums produced by the artist of band"
    )
    List<AlbumDto> albums
) {}
