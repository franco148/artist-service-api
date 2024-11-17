package com.francofral.artistapi.dto;

import java.util.List;

public record ArtistDto(
    Long id,
    String name,
    String profile,
    List<AlbumDto> albums
) {}
