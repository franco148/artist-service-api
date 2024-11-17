package com.francofral.artistapi.client;

import com.francofral.artistapi.config.FeignClientConfiguration;
import com.francofral.artistapi.dto.AlbumDtoWrapper;
import com.francofral.artistapi.dto.ArtistDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(
    name = "${artist.resource.api.name}",
    url = "${artist.resource.api.url}",
    configuration = FeignClientConfiguration.class
)
public interface ArtistSearchClient {

    @GetMapping("/artists/{artist_id}")
    ArtistDto getArtistById(@PathVariable("artist_id") Long artistId);

    @GetMapping("/artists/{artist_id}/releases")
    AlbumDtoWrapper getArtistAlbums(@PathVariable("artist_id") Long artistId);
}
