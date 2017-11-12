package com.github.kirikakis.marvel.character.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.NOT_ACCEPTABLE, reason = "Wiki Url has not found.")
public class WikiUrlNotFound extends Exception {
}
