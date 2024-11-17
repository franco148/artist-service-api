package com.francofral.artistapi.client;

import feign.Response;
import feign.codec.ErrorDecoder;

public final class ArtistSearchErrorDecoder implements ErrorDecoder {

    private final ErrorDecoder errorDecoder;

    public ArtistSearchErrorDecoder() {
        this(new Default());
    }

    public ArtistSearchErrorDecoder(ErrorDecoder errorDecoder) {
        this.errorDecoder = errorDecoder;
    }


    @Override
    public Exception decode(String s, Response response) {

        return null;
    }
}
