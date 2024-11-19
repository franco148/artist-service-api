package com.francofral.artistapi.repository;

import com.francofral.artistapi.domain.Artist;
import com.francofral.artistapi.dto.ArtistComparisonDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Set;

public interface ArtistRepository extends JpaRepository<Artist, Long> {

    @Query(value = """
            SELECT 
                v.artist_id AS artistId,
                v.artist_name AS artistName,
                v.number_of_albums AS numberOfAlbums,
                v.active_years AS activeYears,
                v.genre AS genre
            FROM artist_comparison_view v
            WHERE v.artist_id IN :artistIds
            """, nativeQuery = true)
    Set<ArtistComparisonDto> getComparativeInformationByArtistIds(@Param("artistIds") Set<Long> artistIds);
}
