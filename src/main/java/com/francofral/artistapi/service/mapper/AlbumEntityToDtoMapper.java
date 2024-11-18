package com.francofral.artistapi.service.mapper;

import com.francofral.artistapi.domain.Album;
import com.francofral.artistapi.dto.AlbumDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class AlbumEntityToDtoMapper implements Function<Album, AlbumDto> {

    @Override
    public AlbumDto apply(Album album) {
        return new AlbumDto(album.getId(), album.getTitle(), album.getReleaseYear());
    }
}
