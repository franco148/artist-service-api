package com.francofral.artistapi.service;

import com.francofral.artistapi.dto.AlbumDto;
import com.francofral.artistapi.dto.ArtistComparisonDto;
import com.francofral.artistapi.dto.ArtistComparisonDtoImpl;
import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.repository.ArtistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

class ArtistInfoOrchestrationServiceTest {

    @InjectMocks
    private ArtistInfoOrchestrationService underTest;

    @Mock
    private ArtistService artistService;

    @Mock
    private AlbumService albumService;

    @Mock
    private ArtistRepository artistRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldRetrieveArtistByIdOrCreate() {
        // GIVEN
        Long artistId = 1L;
        List<AlbumDto> albumDtos = Arrays.asList(
                new AlbumDto(1L, "Album 1", 1997),
                new AlbumDto(2L, "Album 2", 2003)
        );
        ArtistDto artistDto = new ArtistDto(1L, "Artist Name", "Profile", "Genre", albumDtos);

        when(artistService.retrieveArtist(artistId)).thenReturn(artistDto);
        when(albumService.retrieveAlbumsByArtistIdAndSave(artistId)).thenReturn(albumDtos);

        // WHEN
        ArtistDto result = underTest.retrieveArtistByIdOrCreate(artistId);

        // THEN
        assertAll(
                () -> assertThat(result).isNotNull(),
                () -> assertThat(result)
                        .usingRecursiveComparison()
                        .isEqualTo(artistDto),
                () -> then(artistService)
                        .should(times(1))
                        .retrieveArtist(artistId),
                () -> then(albumService)
                        .should(times(1))
                        .retrieveAlbumsByArtistIdAndSave(artistId)
        );
    }

    @Test
    void shouldGetComparativeInformationByArtistIds() {
        // GIVEN
        Set<Long> artistIds = Set.of(1L, 2L);
        Set<ArtistComparisonDto> comparisonDtos = Set.of(
                new ArtistComparisonDtoImpl(1L, "Artist 1", 5, 11, "Genre 1"),
                new ArtistComparisonDtoImpl(2L, "Artist 2", 2, 3, "Genre 2")
        );

        when(artistRepository.getComparativeInformationByArtistIds(artistIds)).thenReturn(comparisonDtos);

        // WHEN
        Set<ArtistComparisonDto> result = underTest.getComparativeInformationByArtistIds(artistIds);

        // THEN
        assertAll(
            () -> assertThat(result).isNotNull(),
            () -> assertThat(result).hasSize(comparisonDtos.size()),
            () -> assertThat(result)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(comparisonDtos),
            () -> then(artistRepository).should(times(1)).getComparativeInformationByArtistIds(artistIds)
        );
    }
}