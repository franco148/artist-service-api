package com.francofral.artistapi.service;

import com.francofral.artistapi.dto.AlbumDto;
import com.francofral.artistapi.dto.ArtistDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
public class ArtistInfoOrchestrationService {

    private final ArtistService artistService;
    private final AlbumService albumService;

    @Transactional
    public ArtistDto retrieveArtistByIdOrCreate(Long artistId) {
        ArtistDto artistDto = artistService.retrieveArtist(artistId);
        List<AlbumDto> albumDtoList = albumService.retrieveAlbumsByArtistIdAndSave(artistId, artistDto);

        return new ArtistDto(artistDto.id(), artistDto.name(), artistDto.profile(), albumDtoList);
    }
}
