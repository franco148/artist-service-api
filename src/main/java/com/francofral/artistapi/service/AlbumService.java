package com.francofral.artistapi.service;

import com.francofral.artistapi.client.ArtistSearchClient;
import com.francofral.artistapi.domain.Album;
import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.AlbumDto;
import com.francofral.artistapi.dto.AlbumDtoWrapper;
import com.francofral.artistapi.dto.ArtistDto;
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

    public List<AlbumDto> retrieveAlbumsByArtistIdAndSave(Long artistId, ArtistDto artistDto) {

        List<Album> artistAlbums = albumRepository.findAllByArtistId(artistDto.id());
        if (!artistAlbums.isEmpty()) {
            return artistAlbums.stream()
                    .map(albumEntityToDtoMapper)
                    .collect(Collectors.toList());
        }

        List<AlbumDto> albumDtoList = fetchArtistAlbums(artistId);
        saveAlbums(albumDtoList, artistDto.id());

        return albumDtoList;
    }

    private List<AlbumDto> fetchArtistAlbums(Long artistId) {
        log.info("Fetching artist albums for artist ID: {}", artistId);
        AlbumDtoWrapper albumDtoWrapper = artistSearchClient.getArtistAlbums(artistId);
        log.info("Successfully fetched artist albums. {} albums for artist with ID: {}",
                albumDtoWrapper.albums().size(),
                artistId);

        return albumDtoWrapper.albums();
    }

    private void saveAlbums(List<AlbumDto> albumDtos, Long artistId) {

        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new EntityNotFoundException(Artist.class, artistId));

        List<Album> albums = albumDtos.stream()
                .map(albumDtoToEntityMapper)
                .peek(album -> album.setArtist(artist))
                .toList();

        albumRepository.saveAll(albums);
    }
}
