package com.github.kirikakis.marvel.character.bootstrap

import com.github.kirikakis.marvel.character.MarvelCharacterRepository
import com.github.kirikakis.marvel.character.TestHelper
import com.github.kirikakis.marvel.character.config.MarvelConfig
import com.github.kirikakis.marvel.character.model.MarvelCharacter
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class MarvelCharacterBootstrapTest extends Specification {

    MarvelCharacterBootstrap marvelCharacterBootstrap;

    MarvelConfig marvelConfig;

    RestTemplate restTemplate;

    MarvelCharacterRepository marvelCharacterRepository

    def charactersUrl = "http://example.com"

    List<MarvelCharacter> savedListCharacters

    void setup() {
        marvelConfig = Mock(MarvelConfig.class)
        restTemplate = Mock(RestTemplate.class)
        marvelCharacterRepository = Mock(MarvelCharacterRepository.class)
        marvelCharacterBootstrap = new MarvelCharacterBootstrap();
        marvelCharacterBootstrap.setMarvelConfig(marvelConfig)
        marvelCharacterBootstrap.setMarvelCharacterRepository(marvelCharacterRepository)
    }

    def "shouldLoadAllCharactersFromMarvel"() {
        given:
        def marvelJsonReplyWith3Characters = TestHelper.ReadJsonFileFromResources("MarvelCharactersResponse.json")
        when:
        marvelCharacterBootstrap.loadAllCharactersFromMarvelToMemory(restTemplate)
        then:
        1 * marvelConfig.getCharactersUrl(0) >> charactersUrl
        1 * restTemplate.getForObject(charactersUrl, String.class) >> marvelJsonReplyWith3Characters
        1 * marvelCharacterRepository.save(_) >> {arguments -> savedListCharacters=(List<MarvelCharacter>)arguments[0]}
        savedListCharacters.size() == 3
        savedListCharacters.get(0).id == 1011334
        savedListCharacters.get(0).name == "3-D Man"
        savedListCharacters.get(0).description == ""
        savedListCharacters.get(0).thumbnail.path == "http://i.annihil.us/u/prod/marvel/i/mg/c/e0/535fecbbb9784"
        savedListCharacters.get(0).thumbnail.extension == "jpg"
        savedListCharacters.get(1).id == 1017100
        savedListCharacters.get(1).name == "A-Bomb (HAS)"
        savedListCharacters.get(1).description == "Rick Jones has been Hulk's best bud since day one, but now he's more than a friend...he's a teammate! Transformed by a Gamma energy explosion, A-Bomb's thick, armored skin is just as strong and powerful as it is blue. And when he curls into action, he uses it like a giant bowling ball of destruction! "
        savedListCharacters.get(1).thumbnail.path == "http://i.annihil.us/u/prod/marvel/i/mg/3/20/5232158de5b16"
        savedListCharacters.get(1).thumbnail.extension == "jpg"
        savedListCharacters.get(2).id == 1009144
        savedListCharacters.get(2).name == "A.I.M."
        savedListCharacters.get(2).description == "AIM is a terrorist organization bent on destroying the world."
        savedListCharacters.get(2).thumbnail.path == "http://i.annihil.us/u/prod/marvel/i/mg/6/20/52602f21f29ec"
        savedListCharacters.get(2).thumbnail.extension == "jpg"
    }

    def "shouldLoadCharactersUtilNoMore"() {
        given:
        def marvelJsonReplyWith1Character =
                TestHelper.ReadJsonFileFromResources("MarvelCharactersResponseCount1Total3.json")
        when:
        marvelCharacterBootstrap.loadAllCharactersFromMarvelToMemory(restTemplate)
        then:
        1 * marvelConfig.getCharactersUrl(0) >> charactersUrl
        1 * marvelConfig.getCharactersUrl(1) >> charactersUrl
        1 * marvelConfig.getCharactersUrl(2) >> charactersUrl
        3 * restTemplate.getForObject(charactersUrl, String.class) >> marvelJsonReplyWith1Character
        3 * marvelCharacterRepository.save(_) >> {arguments -> savedListCharacters=(List<MarvelCharacter>)arguments[0]}
        savedListCharacters.size() == 1
    }
}
