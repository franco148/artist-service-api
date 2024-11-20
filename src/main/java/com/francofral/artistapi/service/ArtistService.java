package com.francofral.artistapi.service;

import com.francofral.artistapi.client.ArtistSearchClient;
import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.problem.ResourceNotFoundException;
import com.francofral.artistapi.repository.ArtistRepository;
import com.francofral.artistapi.service.mapper.ArtistDtoToEntityMapper;
import com.francofral.artistapi.service.mapper.ArtistEntityToDtoMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@AllArgsConstructor
@Service
class ArtistService {

    private final ArtistSearchClient artistSearchClient;
    private final ArtistRepository artistRepository;
    private final ArtistDtoToEntityMapper artistDtoToEntityMapper;
    private final ArtistEntityToDtoMapper artistEntityToDtoMapper;

    public ArtistDto retrieveArtist(Long artistId) {

        return artistRepository.findById(artistId)
                .map(artistEntityToDtoMapper)
                .orElseGet(() -> {
                    ArtistDto artistDto = fetchArtist(artistId);
                    Artist artist = saveArtist(artistDto);
                    return artistEntityToDtoMapper.apply(artist);
                });
    }

    private ArtistDto fetchArtist(Long artistId) {
        log.info("Fetching artist with ID: {}", artistId);

        ArtistDto artistDto = artistSearchClient.fetchArtistById(artistId)
                .orElseThrow(() -> new ResourceNotFoundException(String.format("Artist with ID=%s couldn't be found", artistId)));

        log.info("Successfully retrieved artist: {}", artistDto);

        return artistDto;
    }

    private Artist saveArtist(ArtistDto artistDto) {
        Artist artistEntity = artistDtoToEntityMapper.apply(artistDto);
        return artistRepository.save(artistEntity);
    }
}
