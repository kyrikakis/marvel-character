package com.github.kirikakis.marvel.character.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Marvel character has not found.")
public class CharacterNotFoundException extends Exception {
}
