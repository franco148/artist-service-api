package com.francofral.artistapi.config;

import com.francofral.artistapi.client.ArtistSearchErrorDecoder;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FeignClientConfiguration {

    @Bean
    public ErrorDecoder errorDecoder() {
        return new ArtistSearchErrorDecoder();
    }
}
