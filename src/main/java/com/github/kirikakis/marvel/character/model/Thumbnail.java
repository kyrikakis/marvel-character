package com.github.kirikakis.marvel.character.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

import io.swagger.annotations.ApiModel;

@Embeddable
@Access(AccessType.PROPERTY)
@ApiModel(value="Thumbnail", description="The representative image for this character.")
public class Thumbnail {
    private String path;
    private String extension;

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getExtension() {
        return extension;
    }

    public void setExtension(String extension) {
        this.extension = extension;
    }
}
