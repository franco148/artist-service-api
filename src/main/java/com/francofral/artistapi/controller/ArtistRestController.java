package com.francofral.artistapi.controller;

import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.problem.ErrorResponse;
import com.francofral.artistapi.service.ArtistInfoOrchestrationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Artists API", description = "Artists management APIs")
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
    public ResponseEntity<ArtistDto> findArtistById(@PathVariable("artistId") Long artistId) {
        ArtistDto artistDto = artistInfoOrchestrationService.retrieveArtistByIdOrCreate(artistId);
        return ResponseEntity.ok().body(artistDto);
    }
}
