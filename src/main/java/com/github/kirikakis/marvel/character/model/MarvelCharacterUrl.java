package com.github.kirikakis.marvel.character.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MarvelCharacterUrl {
    @JsonProperty("type")
    private String type;
    @JsonProperty("url")
    private String url;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
