package com.francofral.artistapi.dto;

public class ArtistComparisonDtoImpl implements ArtistComparisonDto {

    private final Long artistId;
    private final String artistName;
    private final Integer numberOfAlbums;
    private final Integer activeYears;
    private final String genre;

    public ArtistComparisonDtoImpl() {
        this(null, null, null, null, null);
    }

    public ArtistComparisonDtoImpl(Long artistId, String artistName, Integer numberOfAlbums, Integer activeYears, String genre) {
        this.artistId = artistId;
        this.artistName = artistName;
        this.numberOfAlbums = numberOfAlbums;
        this.activeYears = activeYears;
        this.genre = genre;
    }

    @Override
    public Long getArtistId() {
        return artistId;
    }

    @Override
    public String getArtistName() {
        return artistName;
    }

    @Override
    public Integer getNumberOfAlbums() {
        return numberOfAlbums;
    }

    @Override
    public Integer getActiveYears() {
        return activeYears;
    }

    @Override
    public String getGenre() {
        return genre;
    }
}
