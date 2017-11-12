package com.github.kirikakis.marvel.character;

import com.github.kirikakis.marvel.character.model.MarvelCharacter;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource
public interface MarvelCharacterRepository extends CrudRepository<MarvelCharacter, Integer> {

    @Query("select mc.id from MarvelCharacter mc")
    List<Integer> getAllIds();
}
