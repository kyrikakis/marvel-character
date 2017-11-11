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

    def "ShouldReturnAllMarvelCharacters"() {
        given:
        List<MarvelCharacter> marvelCharacterList = new ArrayList<>()
        when:
        characterService.listAllCharacters() == marvelCharacterList
        then:
        1 * characterRepository.findAll() >> marvelCharacterList
    }
}
