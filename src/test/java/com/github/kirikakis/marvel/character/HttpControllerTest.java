package com.github.kirikakis.marvel.character;

import com.github.kirikakis.marvel.character.model.MarvelCharacter;
import com.github.kirikakis.marvel.character.model.MarvelCharacterPowers;
import com.github.kirikakis.marvel.character.services.MarvelCharacterService;
import com.github.kirikakis.marvel.character.services.TranslateService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import io.swagger.models.Model;

import static org.mockito.BDDMockito.given;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {HttpController.class})
public class HttpControllerTest {

    @Autowired
    private HttpController httpController;

    @MockBean
    MarvelCharacterService marvelCharacterService;

    @MockBean
    TranslateService translateService;

    @Mock
    Model model;

    @Test
    public void listCharactersIds() throws Exception {
        List<Integer> characterList = new ArrayList<>();
        given(marvelCharacterService.getAllIds()).willReturn(characterList);

        assertEquals(characterList, httpController.listCharactersIds(model));
    }

    @Test
    public void showCharacter() throws Exception {
        MarvelCharacter marvelCharacter = new MarvelCharacter(1);
        given(marvelCharacterService.getCharacterById(1)).willReturn(marvelCharacter);

        assertEquals(marvelCharacter, httpController.showCharacter(1, model));
    }

    @Test
    public void showCharacterPowers() throws Exception {
        MarvelCharacter marvelCharacter = new MarvelCharacter(1);
        MarvelCharacterPowers marvelCharacterPowers = new MarvelCharacterPowers();
        given(marvelCharacterService.getCharacterById(1)).willReturn(marvelCharacter);
        given(marvelCharacterService.getCharacterPowers(1)).willReturn(marvelCharacterPowers);

        assertEquals(marvelCharacterPowers,
                httpController.showCharacterPowers(1, null, model));
    }

    @Test
    public void showCharacterPowersTranslated() throws Exception {
        MarvelCharacter marvelCharacter = new MarvelCharacter(1);
        MarvelCharacterPowers marvelCharacterPowers = new MarvelCharacterPowers();
        marvelCharacterPowers.setPowers("awesome coding powers");
        String languageCode = "el";
        String translatedText = "εξαιρετικές δυνατότητες κωδικοποίησης";

        given(marvelCharacterService.getCharacterById(1)).willReturn(marvelCharacter);
        given(marvelCharacterService.getCharacterPowers(1)).willReturn(marvelCharacterPowers);
        given(translateService.translateText(marvelCharacterPowers.getPowers(), languageCode))
                .willReturn(translatedText);

        MarvelCharacterPowers translatedPowers
                = httpController.showCharacterPowers(1, languageCode, model);
        assertEquals(translatedText, translatedPowers.getPowers());
    }
}