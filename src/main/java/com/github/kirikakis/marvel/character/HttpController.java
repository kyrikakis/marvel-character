package com.github.kirikakis.marvel.character;

import com.github.kirikakis.marvel.character.model.MarvelCharacter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.Model;

@RestController
@RequestMapping("/")
@Api(value="onlinestore", description="Operations pertaining to products in Online Store")
public class HttpController {

    private MarvelCharacterService marvelCharacterService;

    @Autowired
    public void setMarvelCharacterService(MarvelCharacterService marvelCharacterService) {
        this.marvelCharacterService = marvelCharacterService;
    }

    @ApiOperation(value = "View a list of Marvel characters",response = Iterable.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved list"),
            @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
            @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    }
    )
    @RequestMapping(value = "/characters", method= RequestMethod.GET, produces = "application/json")
    public Iterable<MarvelCharacter> listCharacters(Model model){
        return marvelCharacterService.listAllCharacters();
    }
}
