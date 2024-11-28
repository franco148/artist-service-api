package com.francofral.artistapi.controller;

import com.francofral.artistapi.dto.AlbumDto;
import com.francofral.artistapi.dto.ArtistComparisonDto;
import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.problem.ErrorResponse;
import com.francofral.artistapi.service.ArtistInfoOrchestrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Pattern.Flag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@Tag(name = "Artists API", description = "Artists management APIs")
@Validated
@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/artists")
public class ArtistRestController {

    private final ArtistInfoOrchestrationService artistInfoOrchestrationService;


    @Operation(
        summary = "Find artist by ID",
        description = """
            It obtains the artist information in the system, if it does not find it, 
            it searches for it in third party APIs and stores it in the system's database
            """
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ArtistDto.class), mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Artist not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
        )
    })
    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistDto> getArtistById(@PathVariable("artistId") @Min(1) Long artistId) {
        ArtistDto artistDto = artistInfoOrchestrationService.retrieveArtistByIdOrCreate(artistId);
        return ResponseEntity.ok(artistDto);
    }


    @Operation(
        summary = "Retrieve artist's discography",
        description = "Retrieve an artist’s discography sorted by release year."
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = AlbumDto.class), mediaType = "application/json")
        )
    })
    @GetMapping("/{artistId}/discography")
    public ResponseEntity<List<AlbumDto>> getArtistAlbums(
            @PathVariable("artistId") @Min(1) Long artistId,
            @RequestParam(value = "sortDirection", defaultValue = "ASC") @Pattern(regexp = "ASC|DESC", flags = Flag.CANON_EQ) String sortDirection) {

        List<AlbumDto> artistDiscography = artistInfoOrchestrationService.retrieveArtistDiscography(artistId, sortDirection);
        return ResponseEntity.ok(artistDiscography);
    }


    @Operation(
        summary = "Find comparisons of the artists.",
        description = """
        Returns comparisons of the specified artists around:
        - Number of albums
        - Active years (from the first album release to the most recent).
        - Genres
        """
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            content = @Content(schema = @Schema(implementation = ArtistComparisonDto.class), mediaType = "application/json")
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Comparisons not found",
            content = @Content(schema = @Schema(implementation = ErrorResponse.class), mediaType = "application/json")
        )
    })
    @GetMapping("/compare")
    public ResponseEntity<Set<ArtistComparisonDto>> getComparativeInformationByArtistIds(@RequestParam("artistId") Set<@Min(1) Long> artistIds) {
        Set<ArtistComparisonDto> artistComparisons = artistInfoOrchestrationService.getComparativeInformationByArtistIds(artistIds);
        return ResponseEntity.ok(artistComparisons);
    }
}
