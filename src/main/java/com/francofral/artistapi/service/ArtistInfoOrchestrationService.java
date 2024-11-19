package com.francofral.artistapi.service;

import com.francofral.artistapi.dto.AlbumDto;
import com.francofral.artistapi.dto.ArtistComparisonDto;
import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@AllArgsConstructor
@Service
public class ArtistInfoOrchestrationService {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final ArtistRepository artistRepository;

    @Transactional
    public ArtistDto retrieveArtistByIdOrCreate(Long artistId) {
        ArtistDto artistDto = artistService.retrieveArtist(artistId);
        List<AlbumDto> albumDtoList = albumService.retrieveAlbumsByArtistIdAndSave(artistId);

        return new ArtistDto(artistDto.id(), artistDto.name(), artistDto.profile(), artistDto.genre(), albumDtoList);
    }

    public Set<ArtistComparisonDto> getComparativeInformationByArtistIds(Set<Long> artistIds) {
        return artistRepository.getComparativeInformationByArtistIds(artistIds);
    }
}
