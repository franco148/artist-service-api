package com.francofral.artistapi.service;

import com.francofral.artistapi.client.ArtistSearchClient;
import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.ArtistDto;
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

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@DisplayName("Artist Service Tests")
@ExtendWith(MockitoExtension.class)
class ArtistServiceTest {

    private static final String ENTITY_NOT_FOUND_TEMPLATE_MESSAGE = "%s with id %s could not be found or it does not exist.";

    private final ArtistEntityToDtoMapper artistEntityToDtoMapper = new ArtistEntityToDtoMapper();
    private final ArtistDtoToEntityMapper artistDtoToEntityMapper = new ArtistDtoToEntityMapper();
    private final AlbumEntityToDtoMapper albumEntityToDtoMapper = new AlbumEntityToDtoMapper();
    private final AlbumDtoToEntityMapper albumDtoToEntityMapper = new AlbumDtoToEntityMapper();

    private ArtistService underTest;

    @Mock
    private ArtistSearchClient artistSearchClient;
    @Mock
    private ArtistRepository artistRepository;

    @BeforeEach
    void setUp() {
        this.underTest = new ArtistService(
                artistSearchClient, artistRepository, artistDtoToEntityMapper, artistEntityToDtoMapper);
    }

    @Test
    @DisplayName("Gets the artist information correctly when it is found in the database")
    void getsArtistSuccessfullyWhenItIsFoundInTheDatabase() {
        // GIVEN
        ArtistDto expected = artistEntityToDtoMapper.apply(getArtistEntity());
        given(artistRepository.findById(anyLong())).willReturn(Optional.of(getArtistEntity()));

        // WHEN
        ArtistDto actual = underTest.retrieveArtist(100L);

        // THEN
        assertAll(
            () -> assertThat(actual).isEqualTo(expected),
            () -> then(artistSearchClient).should(never()).getArtistById(anyLong()),
            () -> then(artistRepository).should(never()).save(any(Artist.class))
        );
    }

    @Test
    @DisplayName("Fetches artist information from third party API when it is not found in the database")
    void fetchArtistsFromThirdPartyApiWhenItDoesNotExistInTheDatabase() {
        // GIVEN
        ArtistDto expected = artistEntityToDtoMapper.apply(getArtistEntity());
        given(artistRepository.findById(anyLong())).willReturn(Optional.empty());
        given(artistRepository.save(any(Artist.class))).willReturn(getArtistEntity());
        given(artistSearchClient.getArtistById(anyLong())).willReturn(expected);

        // WHEN
        ArtistDto actual = underTest.retrieveArtist(100L);

        // THEN
        ArgumentCaptor<Artist> artistArgumentCaptor = ArgumentCaptor.forClass(Artist.class);
        verify(artistRepository).save(artistArgumentCaptor.capture());
        Artist capturedArtist = artistArgumentCaptor.getValue();

        assertAll(
            () -> then(artistRepository).should(atLeastOnce()).findById(anyLong()),
            () -> then(artistSearchClient).should(atLeastOnce()).getArtistById(anyLong()),
            () -> assertThat(capturedArtist.getId()).isEqualTo(expected.id()),
            () -> assertThat(capturedArtist.getName()).isEqualTo(expected.name()),
            () -> assertThat(capturedArtist.getProfile()).isEqualTo(expected.profile())
        );
    }


    private Artist getArtistEntity() {
        Artist artist = new Artist();
        artist.setId(100L);
        artist.setName("Nickelback");
        artist.setProfile("Alternative rock band from Hanna, Alberta (Canada).");

        return artist;
    }

}