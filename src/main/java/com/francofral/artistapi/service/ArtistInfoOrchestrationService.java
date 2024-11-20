package com.francofral.artistapi.service;

import com.francofral.artistapi.dto.AlbumDto;
import com.francofral.artistapi.dto.ArtistComparisonDto;
import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.repository.ArtistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Service that orchestrates artist-related operations, including retrieving artist information,
 * creating new artists, and retrieving comparative data for multiple artists.
 *
 * <p>This class interacts with {@link ArtistService}, {@link AlbumService}, and {@link ArtistRepository}
 * to provide comprehensive artist-related operations.</p>
 */
@AllArgsConstructor
@Service
public class ArtistInfoOrchestrationService {

    private final ArtistService artistService;
    private final AlbumService albumService;
    private final ArtistRepository artistRepository;

    /**
     * Retrieves an artist by their ID and ensures their associated albums are retrieved and saved
     * if they don't exist in the local database.
     *
     * <p>This method is transactional, ensuring that the operations for retrieving the artist
     * and their albums occur within a single transaction.</p>
     *
     * @param artistId The ID of the artist to retrieve or create.
     * @return An {@link ArtistDto} containing the artist's information and their albums.
     */
    @Transactional
    public ArtistDto retrieveArtistByIdOrCreate(Long artistId) {
        ArtistDto artistDto = artistService.retrieveArtist(artistId);
        List<AlbumDto> albumDtoList = albumService.retrieveAlbumsByArtistIdAndSave(artistId);

        return new ArtistDto(artistDto.id(), artistDto.name(), artistDto.profile(), artistDto.genre(), albumDtoList);
    }

    /**
     * Retrieves comparative information for a set of artists based on their IDs.
     *
     * <p>This method queries the {@link ArtistRepository} to fetch comparison data, such as
     * statistics or attributes for multiple artists.</p>
     *
     * @param artistIds A set of artist IDs to retrieve comparison information for.
     * @return A {@link Set} of {@link ArtistComparisonDto} objects containing the comparative data.
     */
    public Set<ArtistComparisonDto> getComparativeInformationByArtistIds(Set<Long> artistIds) {
        return artistRepository.getComparativeInformationByArtistIds(artistIds);
    }
}
