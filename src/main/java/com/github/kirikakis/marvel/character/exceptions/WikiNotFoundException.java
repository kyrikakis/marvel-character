package com.github.kirikakis.marvel.character.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.SERVICE_UNAVAILABLE, reason = "Character wiki page is not responding.")
public class WikiNotFoundException extends Exception {
    public WikiNotFoundException(String message) { super(message); }
}

