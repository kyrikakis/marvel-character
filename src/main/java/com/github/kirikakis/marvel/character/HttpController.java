package com.github.kirikakis.marvel.character;

import com.github.kirikakis.marvel.character.exceptions.CharacterNotFoundException;
import com.github.kirikakis.marvel.character.exceptions.LanguageCodeException;
import com.github.kirikakis.marvel.character.exceptions.WikiNotFoundException;
import com.github.kirikakis.marvel.character.exceptions.WikiUrlNotFound;
import com.github.kirikakis.marvel.character.model.MarvelCharacter;
import com.github.kirikakis.marvel.character.model.MarvelCharacterPowers;
import com.github.kirikakis.marvel.character.services.MarvelCharacterService;
import com.github.kirikakis.marvel.character.services.TranslateService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Model;

@RestController
@RequestMapping("/")
@Api(value="marvel-character", description="Invoking Marvel API, caching characters data in H2")
public class HttpController {

    private MarvelCharacterService marvelCharacterService;
    private TranslateService translateService;

    @Autowired
    public void setMarvelCharacterService(MarvelCharacterService marvelCharacterService) {
        this.marvelCharacterService = marvelCharacterService;
    }

    @Autowired
    public void setTranslateService(TranslateService translateService) {
        this.translateService = translateService;
    }

    @ApiOperation(value = "View a list of all Marvel character ids.", response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list of character ids."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found.")
        }
    )
    @RequestMapping(value = "/characters", method= RequestMethod.GET, produces = "application/json")
    public Iterable<Integer> listCharactersIds(Model model){
        return marvelCharacterService.getAllIds();
    }

    @ApiOperation(value = "Search a Marvel character by id.", response = MarvelCharacter.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved character."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "The character you requested has not found.")
        }
    )
    @RequestMapping(value = "/characters/{characterId}", method= RequestMethod.GET, produces = "application/json")
    public MarvelCharacter showCharacter(@PathVariable Integer characterId, Model model)
            throws CharacterNotFoundException {
        return marvelCharacterService.getCharacterById(characterId);
    }

    @ApiOperation(value = "Get a character's powers by id.", response = MarvelCharacterPowers.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved character powers."),
            @ApiResponse(code = 400, message = "The Language code is wrong."),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource."),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden."),
            @ApiResponse(code = 404, message = "The character you requested has not found."),
            @ApiResponse(code = 406, message = "The character wiki url has not found."),
            @ApiResponse(code = 503, message = "The character wiki page is not responding.")
        }
    )
    @RequestMapping(value = "/characters/{characterId}/powers", method= RequestMethod.GET, produces = "application/json")
    public MarvelCharacterPowers showCharacterPowers(@PathVariable Integer characterId,
                                                     @RequestParam(value = "language", required = false)
                                                             String languageCode, Model model)
            throws WikiNotFoundException, CharacterNotFoundException, WikiUrlNotFound, LanguageCodeException {
        MarvelCharacterPowers characterPowers = marvelCharacterService.getCharacterPowers(characterId);
        if(languageCode != null) {
            characterPowers.setPowers(translateService.translateText(characterPowers.getPowers(), languageCode));
        }
        return characterPowers;
    }
}
