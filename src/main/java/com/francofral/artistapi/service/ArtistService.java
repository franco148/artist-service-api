package com.francofral.artistapi.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.francofral.artistapi.client.ArtistSearchClient;
import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.problem.ResourceNotFoundException;
import com.francofral.artistapi.repository.ArtistRepository;
import com.francofral.artistapi.service.mapper.ArtistDtoToEntityMapper;
import com.francofral.artistapi.service.mapper.ArtistEntityToDtoMapper;
import com.francofral.artistapi.service.mapper.JsonMappingStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.stream.Stream;

/**
 * Service class responsible for managing artist data.
 * This includes retrieving artists from the database or external sources, and saving new artists locally.
 *
 * The service relies on:
 * - {@link ArtistSearchClient} for fetching artist information from an external service.
 * - {@link ArtistRepository} for local database operations.
 * - {@link ArtistDtoToEntityMapper} and {@link ArtistEntityToDtoMapper} for mapping between DTO and entity objects.
 *
 * This class ensures traceable and reliable artist retrieval and persistence workflows.
 */
@Slf4j
@Service
class ArtistService {

    private final ArtistSearchClient artistSearchClient;
    private final ArtistRepository artistRepository;
    private final JsonMappingStrategy<JsonNode, ArtistDto> artistMapper;
    private final ArtistDtoToEntityMapper artistDtoToEntityMapper;
    private final ArtistEntityToDtoMapper artistEntityToDtoMapper;

    public ArtistService(ArtistSearchClient artistSearchClient,
                         ArtistRepository artistRepository,
                         @Qualifier("discogsArtistMapper") JsonMappingStrategy<JsonNode, ArtistDto> artistMapper,
                         ArtistDtoToEntityMapper artistDtoToEntityMapper,
                         ArtistEntityToDtoMapper artistEntityToDtoMapper) {

        this.artistSearchClient = artistSearchClient;
        this.artistRepository = artistRepository;
        this.artistMapper = artistMapper;
        this.artistDtoToEntityMapper = artistDtoToEntityMapper;
        this.artistEntityToDtoMapper = artistEntityToDtoMapper;
    }

    /**
     * Retrieves an artist by ID from the database. If the artist is not found locally,
     * it fetches the artist from an external source and saves it to the database.
     *
     * @param artistId the ID of the artist to retrieve
     * @return the artist information as a {@link ArtistDto}
     */
    public ArtistDto retrieveArtist(Long artistId) {
        log.info("START - Retrieving artist with ID: {}", artistId);

        return artistRepository.findById(artistId)
                .map(artist -> {
                    log.info("SUCCESS - Artist found locally in the database for ID: {}", artistId);
                    return artistEntityToDtoMapper.apply(artist);
                })
                .orElseGet(() -> {
                    log.info("INFO - Artist with ID: {} not found locally. Fetching from external service.", artistId);
                    ArtistDto artistDto = fetchArtist(artistId);
                    Artist artist = saveArtist(artistDto);
                    return artistEntityToDtoMapper.apply(artist);
                });
    }

    /**
     * Fetches an artist from the external client by ID.
     *
     * @param artistId the ID of the artist to fetch
     * @return the artist information as a {@link ArtistDto}
     * @throws ResourceNotFoundException if the artist cannot be found externally
     */
    private ArtistDto fetchArtist(Long artistId) {
        log.info("START - Fetching artist with ID: {} from external service.", artistId);

        JsonNode artistNode = artistSearchClient.fetchArtistById(artistId);
        ArtistDto artistDto = Stream.of(artistNode)
                .map(artistMapper)
                .findFirst()
                .orElseThrow(() -> {
                    log.error("ERROR - Artist with ID: {} not found in external service.", artistId);
                    return new ResourceNotFoundException(String.format("Artist with ID=%s couldn't be found", artistId));
                });

        log.info("SUCCESS - Successfully retrieved artist with ID: {} from external service: {}", artistId, artistDto);

        return artistDto;
    }

    /**
     * Saves an artist entity to the database.
     *
     * @param artistDto the artist data transfer object to be saved
     * @return the saved artist entity as a {@link Artist}
     */
    private Artist saveArtist(ArtistDto artistDto) {
        log.info("START - Saving artist to the database: {}", artistDto);

        Artist artistEntity = artistDtoToEntityMapper.apply(artistDto);
        Artist savedArtist = artistRepository.save(artistEntity);

        log.info("SUCCESS - Artist successfully saved to the database with ID: {}", savedArtist.getId());
        return savedArtist;
    }
}
