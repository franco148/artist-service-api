package com.francofral.artistapi.repository;

import com.francofral.artistapi.dal.AbstractTestContainers;
import com.francofral.artistapi.dto.ArtistComparisonDto;
import com.francofral.artistapi.dto.ArtistComparisonDtoImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.postgresql.util.PSQLException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;

import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ArtistRepositoryTest extends AbstractTestContainers {

    @Autowired
    private ArtistRepository underTest;


//    @Test
    @DisplayName("Gets the expected Artist Comparisons when valid parameters")
    void getsTheExpectedArtistComparisons() {
        // GIVEN
        Set<Long> artistsId = Set.of(1000L, 1001L);
        var expectedArtists = Set.of(
            new ArtistComparisonDtoImpl(1000L, "Adele", 4, 14, "Pop"),
            new ArtistComparisonDtoImpl(1001L, "Drake", 4, 9, "Hip Hop")
        );

        // WHEN
        var actualArtists = underTest.getComparativeInformationByArtistIds(artistsId);

        // THEN
        assertAll(
            () -> assertThat(actualArtists).isNotEmpty(),
            () -> assertThat(actualArtists).hasSize(2),
            () -> assertThat(actualArtists).usingRecursiveComparison().ignoringCollectionOrder().isEqualTo(expectedArtists)
        );
    }

//    @ParameterizedTest
//    @MethodSource("invalidArtistIds")
    @DisplayName("It returns empty result when parameters are invalid artist IDs")
    void returnsEmptyResultWhenInvalidParameters(Set<Long> invalidArtistIds) {
        // GIVEN
        // WHEN
        var actual = underTest.getComparativeInformationByArtistIds(invalidArtistIds);

        // THEN
        assertThat(actual).isEmpty();
    }

//    @Test
    @DisplayName("InvalidDataAccessResourceUsageException is thrown when null is sent as parameter")
    void itThrowsExceptionWhenNullParameter() {
        // GIVEN
        // WHEN
        RuntimeException thrownException = assertThrows(
            InvalidDataAccessResourceUsageException.class,
            () -> underTest.getComparativeInformationByArtistIds(null)
        );

        // THEN
        assertThat(thrownException)
                .isInstanceOf(InvalidDataAccessResourceUsageException.class)
                .hasMessageContaining("syntax error at or near")
                .hasRootCauseInstanceOf(PSQLException.class);
    }

    static Stream<Set<Long>> invalidArtistIds() {
        return Stream.of(
            Set.of(50005L, 55004L),
            Set.of()
        );
    }

}