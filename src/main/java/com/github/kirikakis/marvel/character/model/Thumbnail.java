package com.github.kirikakis.marvel.character.model;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;

@Embeddable
@Access(AccessType.PROPERTY)
class Thumbnail {
    String path;
    String extension;
}
