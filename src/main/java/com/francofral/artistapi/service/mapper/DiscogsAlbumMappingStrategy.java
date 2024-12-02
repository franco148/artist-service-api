package com.francofral.artistapi.service.mapper;

import com.fasterxml.jackson.databind.JsonNode;
import com.francofral.artistapi.dto.AlbumDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Component("discogsAlbumsMapper")
public class DiscogsAlbumMappingStrategy implements JsonMappingStrategy<JsonNode, AlbumDto> {

    @Override
    public AlbumDto apply(JsonNode jsonNode) {

//        if (isNull(jsonNode) || jsonNode.isEmpty() || !jsonNode.has("releases") || !jsonNode.get("releases").isArray()) {
//            return List.of();
//        }

//        List<AlbumDto> albums = new ArrayList<>();
//        for (JsonNode node : jsonNode.get("releases")) {
//            albums.add(new AlbumDto(
//                node.get("id").asLong(),
//                node.get("title").asText(),
//                node.get("year").asInt()
//            ));
//        }

        return new AlbumDto(jsonNode.get("id").asLong(), jsonNode.get("title").asText(), jsonNode.get("year").asInt());
    }
}
