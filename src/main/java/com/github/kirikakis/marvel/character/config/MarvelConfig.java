package com.github.kirikakis.marvel.character.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class MarvelConfig {

    @Value("${marvel.charactersUrl}")
    private String charactersUrl;

    @Value("${marvel.publicApiKey}")
    private String publicApiKey;

    @Value("${marvel.privateApiKey}")
    private String privateApiKey;

    @Value("${marvel.charactersLimit}")
    private Integer charactersLimit;

    Long timestamp;

    String getPublicApiKey() {
        return publicApiKey;
    }

    String getPrivateApiKey() {
        return privateApiKey;
    }

    private Long getTimestamp() {
        return timestamp = System.currentTimeMillis();
    }

    private String getHash() {
        return DigestUtils.md5DigestAsHex((timestamp + privateApiKey + publicApiKey).getBytes());
    }

    public String getCharactersUrl(Integer offset) {
        String characterUri =
                UriComponentsBuilder.fromHttpUrl(charactersUrl)
                        .queryParam("ts", getTimestamp())
                        .queryParam("apikey", publicApiKey)
                        .queryParam("hash", getHash())
                        .queryParam("limit", charactersLimit)
                        .queryParam("offset", offset)
                        .toUriString();
        System.out.println("Marvel Characters URI: " + characterUri);
        return characterUri;
    }
}
