package com.github.kirikakis.marvel.character

import com.github.kirikakis.marvel.character.model.MarvelCharacter
import spock.lang.Shared
import spock.lang.Specification

class MarvelCharacterServiceTest extends Specification {

    @Shared
    MarvelCharacterRepository characterRepository

    @Shared
    MarvelCharacterService characterService

    void setup() {
        characterRepository = Mock(MarvelCharacterRepository)
        characterService = new MarvelCharacterService()
        characterService.setMarvelCharacterRepository(characterRepository)
    }

    def "shouldReturnAllMarvelCharacterIds"() {
        given:
        List<Integer> marvelCharacterListIds = new ArrayList<>()
        when:

        def returnedIds = characterService.allIds
        then:
        1 * characterRepository.getAllIds() >> marvelCharacterListIds
        marvelCharacterListIds == returnedIds
    }

    def "shouldReturnMarvelCharacterById"() {
        given:
        MarvelCharacter marvelCharacter = new MarvelCharacter(1);
        when:
        def returnedCharacter = characterService.getCharacterById(1)
        then:
        1 * characterRepository.findOne(1) >> marvelCharacter
        marvelCharacter == returnedCharacter
    }
}
