package com.francofral.artistapi.service.mapper;

import java.util.function.Function;

public interface JsonMappingStrategy<Json, DTO> extends Function<Json, DTO> {
}
