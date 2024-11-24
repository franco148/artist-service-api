package com.francofral.artistapi.client;

import com.fasterxml.jackson.databind.JsonNode;
import com.francofral.artistapi.config.FeignClientConfiguration;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Optional;

@FeignClient(
    name = "${artist.resource.api.name}",
    url = "${artist.resource.api.url}",
    configuration = FeignClientConfiguration.class
)
public interface ArtistSearchClient {

    @GetMapping("/artists/{artist_id}")
    JsonNode fetchArtistById(@PathVariable("artist_id") Long artistId);

    @GetMapping("/artists/{artist_id}/releases")
    JsonNode fetchAlbumsForArtist(@PathVariable("artist_id") Long artistId);
}
