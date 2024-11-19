package com.francofral.artistapi.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@Table(name = "artists")
@Entity
public class Artist {

    @Id
    private Long id;

    @Column(length = 100, nullable = false)
    private String name;

    private String profile;

    @Column(length = 20)
    private String genre;

    @Override
    public final boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null) return false;
        if (!(obj instanceof Artist artist)) return false;

        return Objects.equals(id, artist.getId());
    }

    @Override
    public final int hashCode() {
        return Objects.hash(id);
    }
}
