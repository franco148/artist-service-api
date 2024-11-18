package com.francofral.artistapi.service.mapper;

import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.ArtistDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.function.Function;

@Component
public final class ArtistEntityToDtoMapper implements Function<Artist, ArtistDto> {

    @Override
    public ArtistDto apply(Artist artist) {
        return new ArtistDto(artist.getId(), artist.getName(), artist.getProfile(), new ArrayList<>());
    }
}
