package com.francofral.artistapi.service.mapper;

import com.francofral.artistapi.domain.Album;
import com.francofral.artistapi.dto.AlbumDto;
import org.springframework.stereotype.Component;

import java.util.function.Function;

@Component
public final class AlbumDtoToEntityMapper implements Function<AlbumDto, Album> {

    @Override
    public Album apply(AlbumDto albumDto) {
        Album album = new Album();

        album.setId(albumDto.id());
        album.setTitle(albumDto.title());
        album.setReleaseYear(albumDto.releaseYear());

        return album;
    }
}
