package com.francofral.artistapi.repository;

import com.francofral.artistapi.dal.AbstractTestContainers;
import com.francofral.artistapi.domain.Album;
import com.francofral.artistapi.domain.Artist;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class AlbumRepositoryTest extends AbstractTestContainers {

    @Autowired
    private AlbumRepository underTest;

    @Test
    @DisplayName("Gets the expected Albums when valid parameters")
    void getsExpectedArtistAlbumsWhenValidParameter() {
        // GIVEN
        Long duaLipaId = 1018L;
        Set<Album> expectedAlbums = Set.of(
            new Album(2071L, "Dua Lipa", 2017, new Artist()),
            new Album(2072L, "Future Nostalgia", 2020, new Artist())
        );

        // WHEN
        var actualAlbums = underTest.findAllByArtistId(duaLipaId);

        // THEN
        assertAll(
            () -> assertThat(actualAlbums).isNotEmpty(),
            () -> assertThat(actualAlbums).hasSize(2),
            () -> assertThat(actualAlbums)
                .usingRecursiveComparison()
                .ignoringFields("artist")
                .isEqualTo(expectedAlbums)
        );

    }

    @ParameterizedTest
    @ValueSource(longs = { -100L, 0, 5000})
    @DisplayName("It returns empty result when parameter is invalid artist ID")
    void returnsEmptyResultWhenInvalidParameters(long artistId) {
        // GIVEN
        // WHEN
        var actualAlbums = underTest.findAllByArtistId(artistId);

        // THEN
        assertThat(actualAlbums).isEmpty();
    }

    @Test
    @DisplayName("It returns empty result when null is sent as parameter")
    void returnsEmptyResultWhenNullParameter() {
        // GIVEN
        // WHEN
        var actualAlbums = underTest.findAllByArtistId(null);

        // THEN
        assertThat(actualAlbums).isEmpty();
    }
}