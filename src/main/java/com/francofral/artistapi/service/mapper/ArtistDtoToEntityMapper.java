package com.francofral.artistapi.service.mapper;

import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.ArtistDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class ArtistDtoToEntityMapper implements Function<ArtistDto, Artist> {

    @Override
    public Artist apply(ArtistDto artistDto) {

        Artist artist = new Artist();

        artist.setId(artistDto.id());
        artist.setName(artistDto.name());
        artist.setProfile(artistDto.profile());
        artist.setGenre(artistDto.genre());

        return artist;
    }
}
