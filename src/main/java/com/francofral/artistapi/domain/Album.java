package com.francofral.artistapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(
    name = "albums",
    indexes = @Index(name = "idx_artist_id", columnList = "artist_id")
)
@Entity
public class Album {

    @Id
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer releaseYear;

    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Album album)) return false;

        return Objects.equals(id, album.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
