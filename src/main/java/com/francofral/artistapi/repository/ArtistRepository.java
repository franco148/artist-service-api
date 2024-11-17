package com.francofral.artistapi.repository;

import com.francofral.artistapi.domain.Artist;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
