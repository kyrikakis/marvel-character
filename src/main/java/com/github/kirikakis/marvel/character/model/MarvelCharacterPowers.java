package com.github.kirikakis.marvel.character.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import io.swagger.annotations.ApiModel;

@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="MarvelCharacterPowers", description="The Marvel character's powers.")
public class MarvelCharacterPowers {

    private String powers;

    public String getPowers() {
        return powers;
    }

    public void setPowers(String power) {
        this.powers = power;
    }
}
