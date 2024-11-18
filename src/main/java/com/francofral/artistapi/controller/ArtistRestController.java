package com.francofral.artistapi.controller;

import com.francofral.artistapi.dto.ArtistDto;
import com.francofral.artistapi.service.ArtistInfoOrchestrationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/artists")
public class ArtistRestController {

    private final ArtistInfoOrchestrationService artistInfoOrchestrationService;


    @GetMapping("/{artistId}")
    public ResponseEntity<ArtistDto> findArtistById(@PathVariable("artistId") Long artistId) {
        ArtistDto artistDto = artistInfoOrchestrationService.retrieveArtistByIdOrCreate(artistId);
        return ResponseEntity.ok().body(artistDto);
    }
}
