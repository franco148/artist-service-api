package com.francofral.artistapi.service;

import com.francofral.artistapi.client.ArtistSearchClient;
import com.francofral.artistapi.domain.Album;
import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.AlbumDto;
import com.francofral.artistapi.dto.AlbumDtoWrapper;
import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.problem.EntityNotFoundException;
import com.francofral.artistapi.repository.AlbumRepository;
import com.francofral.artistapi.repository.ArtistRepository;
import com.francofral.artistapi.service.mapper.AlbumDtoToEntityMapper;
import com.francofral.artistapi.service.mapper.AlbumEntityToDtoMapper;
import com.francofral.artistapi.service.mapper.ArtistDtoToEntityMapper;
import com.francofral.artistapi.service.mapper.ArtistEntityToDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DisplayName("Album Service Test")
@ExtendWith(MockitoExtension.class)
class AlbumServiceTest {

    private static final String ENTITY_NOT_FOUND_TEMPLATE_MESSAGE = "%s with id %s could not be found or it does not exist.";

    private final ArtistDtoToEntityMapper artistDtoToEntityMapper = new ArtistDtoToEntityMapper();
    private final ArtistEntityToDtoMapper artistEntityToDtoMapper = new ArtistEntityToDtoMapper();
    private final AlbumEntityToDtoMapper albumEntityToDtoMapper = new AlbumEntityToDtoMapper();
    private final AlbumDtoToEntityMapper albumDtoToEntityMapper = new AlbumDtoToEntityMapper();

    private AlbumService underTest;

    @Mock
    private ArtistSearchClient artistSearchClient;
    @Mock
    private ArtistRepository artistRepository;
    @Mock
    private AlbumRepository albumRepository;


    @BeforeEach
    void setUp() {
        this.underTest = new AlbumService(
                artistSearchClient,
                artistRepository,
                albumRepository,
                albumDtoToEntityMapper,
                albumEntityToDtoMapper,
                artistDtoToEntityMapper
        );
    }

    @Test
    @DisplayName("Gets albums information correctly when they are found in the database")
    void getsAlbumsSuccessfullyWhenTheyAreFoundInTheDatabase() {
        // GIVEN
        List<AlbumDto> expectedList = getAlbumEntities().stream()
                                .map(albumEntityToDtoMapper)
                                .toList();
        given(albumRepository.findAllByArtistId(anyLong())).willReturn(getAlbumEntities());

        // WHEN
        List<AlbumDto> actual = underTest.retrieveAlbumsByArtistIdAndSave(100L);

        // THEN
        assertAll(
            () -> then(albumRepository).should().findAllByArtistId(anyLong()),
            () -> then(artistSearchClient).should(never()).getArtistAlbums(anyLong()),
            () -> then(artistRepository).should(never()).findById(anyLong()),
            () -> then(albumRepository).should(never()).saveAll(anyList()),
            () -> assertThat(actual).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList)
        );
    }

    @Test
    @DisplayName("EntityNotFoundException is thrown when artist is not found")
    void throwsExceptionWhenArtistNotFound() {
        // GIVEN
        given(albumRepository.findAllByArtistId(anyLong())).willReturn(Collections.emptyList());
        given(artistSearchClient.getArtistAlbums(anyLong())).willReturn(getAlbumDtoWrapper());
        given(artistRepository.findById(anyLong())).willReturn(Optional.empty());

        // WHEN
        RuntimeException thrownException = assertThrows(
            EntityNotFoundException.class,
            () -> underTest.retrieveAlbumsByArtistIdAndSave(100L)
        );

        // THEN
        assertAll(
            () -> assertThat(thrownException)
                    .isInstanceOf(EntityNotFoundException.class)
                    .hasMessage(ENTITY_NOT_FOUND_TEMPLATE_MESSAGE, Artist.class.getSimpleName(), 100L),
            () -> then(albumRepository).should().findAllByArtistId(anyLong()),
            () -> then(artistSearchClient).should().getArtistAlbums(anyLong()),
            () -> then(artistRepository).should().findById(anyLong()),
            () -> then(albumRepository).should(never()).saveAll(anyList())
        );
    }

//    @Test
    @DisplayName("Fetches albums information from third party API when it is not found in the database")
    void fetchAlbumsFromThirdPartyApiWhenItDoesNotExistInTheDatabase() {
        // GIVEN
        List<Album> expectedList = getAlbumEntities();
        given(albumRepository.findAllByArtistId(anyLong())).willReturn(Collections.emptyList());
        given(artistSearchClient.getArtistAlbums(anyLong())).willReturn(getAlbumDtoWrapper());
        given(artistRepository.findById(anyLong())).willReturn(Optional.of(getArtistEntity()));

        // WHEN
        List<AlbumDto> actual = underTest.retrieveAlbumsByArtistIdAndSave(100L);

        // THEN
        ArgumentCaptor<List<Album>> albumsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(albumRepository).saveAll(albumsArgumentCaptor.capture());
        List<Album> capturedAlbums = albumsArgumentCaptor.getValue();

        assertAll(
            () -> then(albumRepository).should().findAllByArtistId(anyLong()),
            () -> then(artistSearchClient).should().getArtistAlbums(anyLong()),
            () -> then(artistRepository).should().findById(anyLong()),
            () -> assertThat(capturedAlbums).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedList)
        );
    }

    private List<Album> getAlbumEntities() {
        Album album1 = new Album();
        album1.setId(201L);
        album1.setTitle("Album 1");
        album1.setReleaseYear(1997);

        Album album2 = new Album();
        album2.setId(202L);
        album2.setTitle("Album 2");
        album2.setReleaseYear(2011);

        return List.of(album1, album2);
    }

    private Artist getArtistEntity() {
        Artist artist = new Artist();
        artist.setId(100L);
        artist.setName("Nickelback");
        artist.setProfile("Alternative rock band from Hanna, Alberta (Canada).");

        return artist;
    }

    private AlbumDtoWrapper getAlbumDtoWrapper() {
        List<AlbumDto> albumDtos = getAlbumEntities().stream()
                .map(albumEntityToDtoMapper)
                .toList();

        return new AlbumDtoWrapper(albumDtos);
    }
}