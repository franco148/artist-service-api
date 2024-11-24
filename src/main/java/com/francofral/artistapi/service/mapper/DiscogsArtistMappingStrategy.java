package com.francofral.artistapi.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.francofral.artistapi.dto.ArtistDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.isNull;

@Component("discogsArtistMapper")
public class DiscogsArtistMappingStrategy implements JsonMappingStrategy<JsonNode, ArtistDto> {

    @Override
    public ArtistDto apply(JsonNode jsonNode) {

        if (isNull(jsonNode) || jsonNode.isEmpty()) {
            return null;
        }

        return new ArtistDto(
            jsonNode.get("id").asLong(),
            jsonNode.get("name").asText(),
            jsonNode.get("profile").asText(),
            jsonNode.path("genre").asText(null),
            List.of()
        );
    }
}
