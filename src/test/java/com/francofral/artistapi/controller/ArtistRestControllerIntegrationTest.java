package com.francofral.artistapi.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.francofral.artistapi.dto.ArtistComparisonDto;
import com.francofral.artistapi.dto.ArtistComparisonDtoImpl;
import com.francofral.artistapi.dto.ArtistDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ArtistRestControllerIntegrationTest {

    @Autowired
    private WebTestClient testClient;

    private ObjectMapper objectMapper = new ObjectMapper();

    private static final Random RANDOM = new Random();
    private static final String ARTIST_ENDPOINT_URI = "/api/v1/artists";

    @Test
    void canRetrieveArtistBySpecifiedId() throws JsonProcessingException {
        Long artistId = 1005L;
        var expectedArtist = getArtistDto();

        var actualArtist = testClient.get()
                .uri(ARTIST_ENDPOINT_URI + "/{customerId}", artistId)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<ArtistDto>() {})
                .returnResult()
                .getResponseBody();

        assertAll(
            () -> assertThat(actualArtist).isNotNull(),
            () -> assertThat(actualArtist).isEqualToComparingFieldByFieldRecursively(expectedArtist)
        );
    }

    @Test
    void canRetrieveArtistComparisonsForSpecifiedIds() throws JsonProcessingException {
        Set<Long> artistsIds = Set.of(1005L, 1006L);
        Long artistId1 = 1005L;
        Long artistId2 = 1006L;
        List<ArtistComparisonDtoImpl> expectedComparisons = getArtistsComparedDto();

        var actualComparisons = testClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path(ARTIST_ENDPOINT_URI + "/compare")
                        .queryParam("artistId", artistId1)
                        .queryParam("artistId", artistId2)
                        .build())
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(new ParameterizedTypeReference<List<ArtistComparisonDtoImpl>>() {})
                .returnResult()
                .getResponseBody();

        assertAll(
            () -> assertThat(actualComparisons).isNotEmpty(),
            () -> assertThat(actualComparisons).hasSize(expectedComparisons.size()),
            () -> assertThat(actualComparisons)
                    .usingRecursiveComparison()
                    .ignoringCollectionOrder()
                    .isEqualTo(expectedComparisons)
        );
    }

    private ArtistDto getArtistDto() throws JsonProcessingException {
        String jsonArtistDto = """
                {
                  "id": 1005,
                  "name": "The Weeknd",
                  "profile": "Long profile description 1 ...",
                  "genre": "R&B",
                  "albums": [
                    {
                      "id": 2020,
                      "title": "Kiss Land",
                      "year": 2013
                    },
                    {
                      "id": 2021,
                      "title": "Beauty Behind the Madness",
                      "year": 2015
                    },
                    {
                      "id": 2022,
                      "title": "Starboy",
                      "year": 2016
                    },
                    {
                      "id": 2023,
                      "title": "After Hours",
                      "year": 2020
                    }
                  ]
                }
                """;
        return objectMapper.readValue(jsonArtistDto, ArtistDto.class);
    }

    private List<ArtistComparisonDtoImpl> getArtistsComparedDto() throws JsonProcessingException {
        String jsonArtistComparisonDto = """
                [
                  {
                    "artistId": 1005,
                    "artistName": "The Weeknd",
                    "numberOfAlbums": 4,
                    "activeYears": 8,
                    "genre": "R&B"
                  },
                  {
                    "artistId": 1006,
                    "artistName": "Bruno Mars",
                    "numberOfAlbums": 3,
                    "activeYears": 7,
                    "genre": "Pop/R&B"
                  }
                ]
                """;

        return objectMapper.readValue(jsonArtistComparisonDto, new TypeReference<List<ArtistComparisonDtoImpl>>() {});
    }
}