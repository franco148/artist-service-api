package com.francofral.artistapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ArtistComparisonDto", description = "DTO that represents the artist comparison information")
public record ArtistComparisonDto(
    @Schema(
        name = "artistId",
        description = "The artist ID"
    )
    Long artistId,

    @Schema(
        name = "artistName",
        description = "The name of the artist or band",
        example = "Teddy Swims"
    )
    String artistName,

    @Schema(
        name = "numberOfAlbums",
        description = "The number of albums released by the artist"
    )
    Integer numberOfAlbums,

    @Schema(
        name = "activeYears",
        description = "The time the artist has been active since the first release of their first album"
    )
    Integer activeYears,

    @Schema(
        name = "activeYears",
        description = "Genre to which the artist's music belongs"
    )
    String genre
) {}
