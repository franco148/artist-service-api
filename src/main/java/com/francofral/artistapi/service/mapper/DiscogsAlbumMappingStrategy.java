package com.francofral.artistapi.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.francofral.artistapi.dto.AlbumDto;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component("discogsAlbumsMapper")
public class DiscogsAlbumMappingStrategy implements JsonMappingStrategy<JsonNode, AlbumDto> {

    @Override
    public AlbumDto apply(JsonNode jsonNode) {

        if (isNull(jsonNode) || jsonNode.isEmpty()) {
            return null;
        }

        return new AlbumDto(
                jsonNode.get("id").asLong(),
                jsonNode.get("title").asText(),
                jsonNode.get("year").asInt()
        );
    }
}
