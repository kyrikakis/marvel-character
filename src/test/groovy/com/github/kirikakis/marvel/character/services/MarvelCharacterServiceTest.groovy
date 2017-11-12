package com.github.kirikakis.marvel.character.services

import com.github.kirikakis.marvel.character.MarvelCharacterRepository
import com.github.kirikakis.marvel.character.exceptions.CharacterNotFoundException
import com.github.kirikakis.marvel.character.exceptions.WikiNotFoundException
import com.github.kirikakis.marvel.character.exceptions.WikiUrlNotFound
import com.github.kirikakis.marvel.character.model.MarvelCharacter
import spock.lang.Specification

class MarvelCharacterServiceTest extends Specification {

    MarvelCharacterRepository characterRepository
    MarvelCharacterService characterService

    void setup() {
        characterRepository = Mock(MarvelCharacterRepository)
        characterService = new MarvelCharacterService()
        characterService.setMarvelCharacterRepository(characterRepository)
    }

    def "getCharacterByIdShouldReturnAllMarvelCharacterIds"() {
        given:
        List<Integer> marvelCharacterListIds = new ArrayList<>()
        when:

        def returnedIds = characterService.allIds
        then:
        1 * characterRepository.getAllIds() >> marvelCharacterListIds
        returnedIds == marvelCharacterListIds
    }

    def "getCharacterByIdShouldReturnMarvelCharacterById"() {
        given:
        MarvelCharacter marvelCharacter = new MarvelCharacter(1);
        when:
        def returnedCharacter = characterService.getCharacterById(1)
        then:
        1 * characterRepository.findOne(1) >> marvelCharacter
        returnedCharacter == marvelCharacter
    }

    def "getCharacterByIdShouldThrowCharacterNotFoundException"() {
        when:
        characterService.getCharacterById(1)
        then:
        1 * characterRepository.findOne(1) >> null
        thrown(CharacterNotFoundException)
    }

    def "getCharacterPowersShouldReturnMarvelCharacterPowerById"() {
        given:
        def expectedCharacterPowersText =
                com.github.kirikakis.marvel.character.TestHelper.ReadFileFromResources("MarvelCharacterPowers.txt")
        def wikiUrl = "http://marvel.com/universe/3-D_Man_(Chandler)?utm_campaign=apiRef&utm_source=debb99fafc067d709ed11e9e2014343d#axzz4yDMnKtse"
        MarvelCharacter marvelCharacter = new MarvelCharacter(1)
        marvelCharacter.setWikiUrl(wikiUrl)
        when:
        def returnedCharacterPowers = characterService.getCharacterPowers(1)
        then:
        1 * characterRepository.findOne(1) >> marvelCharacter
        returnedCharacterPowers.getPowers() == expectedCharacterPowersText
    }

    def "getCharacterPowersShouldThrowWikiUrlNotFoundException"() {
        given:
        MarvelCharacter marvelCharacter = new MarvelCharacter(1)
        when:
        def returnedCharacterPowers = characterService.getCharacterPowers(1)
        then:
        1 * characterRepository.findOne(1) >> marvelCharacter
        thrown(WikiUrlNotFound)
    }

    def "getCharacterPowersShouldThrowWikiNotFoundException"() {
        given:
        def wikiUrl = "http://debb99fafc067d709ed11e9e2014343d#axzz4yDMnKtse"
        MarvelCharacter marvelCharacter = new MarvelCharacter(1)
        marvelCharacter.setWikiUrl(wikiUrl)
        when:
        characterService.getCharacterPowers(1)
        then:
        1 * characterRepository.findOne(1) >> marvelCharacter
        def ex = thrown(WikiNotFoundException)
        ex.message == "class java.net.UnknownHostException debb99fafc067d709ed11e9e2014343d"
    }

    def "getCharacterPowersShouldThrowCharacterNotFoundException"() {
        when:
        characterService.getCharacterPowers(1)
        then:
        1 * characterRepository.findOne(1) >> null
        thrown(CharacterNotFoundException)
    }
}
