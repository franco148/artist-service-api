package com.francofral.artistapi.service;

import com.francofral.artistapi.client.ArtistSearchClient;
import com.francofral.artistapi.domain.Album;
import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.AlbumDto;
import com.francofral.artistapi.dto.AlbumDtoWrapper;
import com.francofral.artistapi.problem.EntityNotFoundException;
import com.francofral.artistapi.problem.ResourceNotFoundException;
import com.francofral.artistapi.repository.AlbumRepository;
import com.francofral.artistapi.repository.ArtistRepository;
import com.francofral.artistapi.service.mapper.AlbumDtoToEntityMapper;
import com.francofral.artistapi.service.mapper.AlbumEntityToDtoMapper;
import com.francofral.artistapi.service.mapper.ArtistDtoToEntityMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class responsible for managing album data. This includes retrieving albums
 * locally or from an external service and saving them to the database.
 *
 * The service relies on:
 * - {@link ArtistSearchClient} for fetching album information from an external service.
 * - {@link AlbumRepository} for local database operations related to albums.
 * - {@link ArtistRepository} for retrieving artist details.
 * - {@link AlbumDtoToEntityMapper} and {@link AlbumEntityToDtoMapper} for mapping between DTO and entity objects.
 *
 * This class ensures traceable workflows for album retrieval and persistence.
 */
@Slf4j
@AllArgsConstructor
@Service
class AlbumService {

    private final ArtistSearchClient artistSearchClient;
    private final ArtistRepository artistRepository;
    private final AlbumRepository albumRepository;
    private final AlbumDtoToEntityMapper albumDtoToEntityMapper;
    private final AlbumEntityToDtoMapper albumEntityToDtoMapper;
    private final ArtistDtoToEntityMapper artistDtoToEntityMapper;


    /**
     * Retrieves albums for the given artist ID. If albums are not found locally, they are fetched
     * from an external service and saved to the database.
     *
     * @param artistId the ID of the artist whose albums are to be retrieved
     * @return a list of {@link AlbumDto} representing the artist's albums
     * @throws ResourceNotFoundException if the albums cannot be found in the external service
     */
    public List<AlbumDto> retrieveAlbumsByArtistIdAndSave(Long artistId) {
        log.info("START - Retrieving albums for artist with ID: {}", artistId);

        List<Album> artistAlbums = albumRepository.findAllByArtistId(artistId);
        if (!artistAlbums.isEmpty()) {
            log.info("SUCCESS - Found {} albums locally for artist with ID: {}", artistAlbums.size(), artistId);
            return artistAlbums.stream()
                    .map(albumEntityToDtoMapper)
                    .collect(Collectors.toList());
        }

        log.info("INFO - No albums found locally for artist with ID: {}. Fetching from external service.", artistId);

        List<AlbumDto> albumDtoList = fetchAlbumsForArtist(artistId);
        saveAlbums(albumDtoList, artistId);

        log.info("SUCCESS - Successfully retrieved and saved {} albums for artist with ID: {}", albumDtoList.size(), artistId);

        return albumDtoList;
    }

    /**
     * Fetches albums for a given artist from an external service.
     *
     * @param artistId the ID of the artist whose albums are to be fetched
     * @return a list of {@link AlbumDto} representing the artist's albums
     * @throws ResourceNotFoundException if albums cannot be found in the external service
     */
    private List<AlbumDto> fetchAlbumsForArtist(Long artistId) {
        log.info("START - Fetching albums for artist with ID: {} from external service.", artistId);

        AlbumDtoWrapper albumDtoWrapper = artistSearchClient.fetchAlbumsForArtist(artistId)
                .orElseThrow(() -> {
                    log.error("ERROR - Albums for artist with ID: {} could not be found in external service.", artistId);
                    return new ResourceNotFoundException(String.format("Albums for artist with ID=%s couldn't be found", artistId));
                });

        log.info("SUCCESS - Fetched {} albums for artist with ID: {} from external service.", albumDtoWrapper.albums().size(), artistId);

        return albumDtoWrapper.albums();
    }

    /**
     * Saves a list of albums to the database for the specified artist.
     *
     * @param albumDtos the list of {@link AlbumDto} to be saved
     * @param artistId  the ID of the artist whose albums are to be saved
     * @throws EntityNotFoundException if the artist cannot be found locally
     */
    private void saveAlbums(List<AlbumDto> albumDtos, Long artistId) {
        log.info("START - Saving {} albums for artist with ID: {}", albumDtos.size(), artistId);

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> {
                    log.error("ERROR - Artist with ID: {} could not be found in the database.", artistId);
                    return new EntityNotFoundException(Artist.class, artistId);
                });

        List<Album> albums = albumDtos.stream()
                .map(albumDtoToEntityMapper)
                .peek(album -> album.setArtist(artist))
                .toList();

        albumRepository.saveAll(albums);

        log.info("SUCCESS - Successfully saved {} albums for artist with ID: {}", albums.size(), artistId);
    }
}
