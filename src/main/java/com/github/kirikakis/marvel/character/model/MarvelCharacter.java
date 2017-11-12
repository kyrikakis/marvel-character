package com.github.kirikakis.marvel.character.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@ApiModel(value="MarvelCharacter", description="Marvel character model")
public class MarvelCharacter {

    @Id
    @ApiModelProperty(notes = "The Marvel character ID")
    private Integer id;

    @ApiModelProperty(notes = "The Marvel character Name")
    private String name;

    @ApiModelProperty(notes = "The Marvel character Description")
    @Column( length = 100000 )
    private String description;

    @ApiModelProperty(notes = "The Marvel character Thumbnail")
    @Embedded
    private Thumbnail thumbnail;

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
}
