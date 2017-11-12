package com.github.kirikakis.marvel.character.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.BAD_REQUEST, reason = "Language code is wrong.")
public class LanguageCodeException extends Exception {
}
