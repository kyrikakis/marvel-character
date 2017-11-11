package com.github.kirikakis.marvel.character;

import com.github.kirikakis.marvel.character.model.MarvelCharacter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MarvelCharacterService {

    private MarvelCharacterRepository marvelCharacterRepository;

    @Autowired
    public void setMarvelCharacterRepository(MarvelCharacterRepository marvelCharacterRepository) {
        this.marvelCharacterRepository = marvelCharacterRepository;
    }

    public Iterable<MarvelCharacter> listAllCharacters() {
        return marvelCharacterRepository.findAll();
    }
}
