package com.github.kirikakis.marvel.character.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="MarvelCharacter", description="The Marvel character model.")
public class MarvelCharacter {

    @Id
    @ApiModelProperty(notes = "The Marvel character unique identifier.")
    private Integer id;

    @ApiModelProperty(notes = "The Marvel character Name.")
    private String name;

    @ApiModelProperty(notes = "The Marvel character Description.")
    @Column( length = 100000 )
    private String description;

    @ApiModelProperty(notes = "The Marvel character Thumbnail.")
    @Embedded
    private Thumbnail thumbnail;

    @ApiModelProperty(hidden = true)
    private String wikiUrl;

    public MarvelCharacter() {}

    public MarvelCharacter(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Thumbnail getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(Thumbnail thumbnail) {
        this.thumbnail = thumbnail;
    }

    @JsonIgnore
    public String getWikiUrl() {
        return wikiUrl;
    }

    @JsonIgnore
    public void setWikiUrl(String wikiUrl) {
        this.wikiUrl = wikiUrl;
    }

    @JsonProperty("urls")
    public void setWikiUrlFromList(List<MarvelCharacterUrl> urls) {
        for(MarvelCharacterUrl url : urls) {
            if(url.getType().equals("wiki")) {
                this.wikiUrl = url.getUrl();
            }
        }
    }
}
