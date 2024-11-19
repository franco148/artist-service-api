package com.francofral.artistapi.dto;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ArtistComparisonDto", description = "DTO that represents the artist comparison information")
public interface ArtistComparisonDto {

    @Schema(
        name = "artistId",
        description = "The artist ID"
    )
    Long getArtistId();

    @Schema(
        name = "artistName",
        description = "The name of the artist or band",
        example = "Teddy Swims"
    )
    String getArtistName();

    @Schema(
        name = "numberOfAlbums",
        description = "The number of albums released by the artist"
    )
    Integer getNumberOfAlbums();

    @Schema(
        name = "activeYears",
        description = "The time the artist has been active since the first release of their first album"
    )
    Integer getActiveYears();

    @Schema(
        name = "activeYears",
        description = "Genre to which the artist's music belongs"
    )
    String getGenre();
}
