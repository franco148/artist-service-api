package com.francofral.artistapi.repository;

import com.francofral.artistapi.domain.Album;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AlbumRepository extends JpaRepository<Album, Long> {

    @Query("SELECT a FROM Album a WHERE a.artist.id = :artistId")
    List<Album> findAllByArtistId(Long artistId);
}
