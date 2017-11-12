package com.github.kirikakis.marvel.character.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
@Configuration
public class MarvelConfig {

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Value("${marvel.charactersUrl}")
    private String charactersUrl;

    @Value("${marvel.publicApiKey}")
    private String publicApiKey;

    @Value("${marvel.privateApiKey}")
    private String privateApiKey;

    @Value("${marvel.charactersLimit}")
    private Integer charactersLimit;

    Long timestamp;

    public void setCharactersUrl(String charactersUrl) {
        this.charactersUrl = charactersUrl;
    }

    public String getPublicApiKey() {
        return publicApiKey;
    }

    public void setPublicApiKey(String publicApiKey) {
        this.publicApiKey = publicApiKey;
    }

    public String getPrivateApiKey() {
        return privateApiKey;
    }

    public void setPrivateApiKey(String privateApiKey) {
        this.privateApiKey = privateApiKey;
    }

    public Integer getCharactersLimit() {
        return charactersLimit;
    }

    public void setCharactersLimit(Integer charactersLimit) {
        this.charactersLimit = charactersLimit;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
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
