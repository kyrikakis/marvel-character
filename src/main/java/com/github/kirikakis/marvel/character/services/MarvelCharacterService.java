package com.github.kirikakis.marvel.character.services;

import com.github.kirikakis.marvel.character.exceptions.CharacterNotFoundException;
import com.github.kirikakis.marvel.character.MarvelCharacterRepository;
import com.github.kirikakis.marvel.character.exceptions.WikiNotFoundException;
import com.github.kirikakis.marvel.character.exceptions.WikiUrlNotFound;
import com.github.kirikakis.marvel.character.model.MarvelCharacter;
import com.github.kirikakis.marvel.character.model.MarvelCharacterPowers;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MarvelCharacterService {

    private MarvelCharacterRepository marvelCharacterRepository;

    @Autowired
    public void setMarvelCharacterRepository(MarvelCharacterRepository marvelCharacterRepository) {
        this.marvelCharacterRepository = marvelCharacterRepository;
    }

    public Iterable<Integer> getAllIds() {
        return marvelCharacterRepository.getAllIds();
    }

    public MarvelCharacter getCharacterById(Integer characterId) throws CharacterNotFoundException {
        MarvelCharacter marvelCharacter = marvelCharacterRepository.findOne(characterId);
        if (marvelCharacter == null) throw new CharacterNotFoundException();
        return marvelCharacter;
    }

    public MarvelCharacterPowers getCharacterPowers(Integer characterId)
            throws WikiNotFoundException, CharacterNotFoundException, WikiUrlNotFound {
        MarvelCharacterPowers marvelCharacterPowers = new MarvelCharacterPowers();
        MarvelCharacter marvelCharacter = marvelCharacterRepository.findOne(characterId);
        if(marvelCharacter == null) throw new CharacterNotFoundException();
        try {
            if(marvelCharacter.getWikiUrl() == null) { throw new WikiUrlNotFound(); }
            Document doc = Jsoup.connect(marvelCharacter.getWikiUrl()).get();
            doc.select("#char-powers-content")
                    .forEach(p -> marvelCharacterPowers.setPowers(p.text()));
        } catch (IOException e) {
            String errorMessage = e.getClass().toString() + " " + e.getMessage();
            System.err.println(errorMessage);
            throw new WikiNotFoundException(errorMessage);
        }
        return marvelCharacterPowers;
    }
}
