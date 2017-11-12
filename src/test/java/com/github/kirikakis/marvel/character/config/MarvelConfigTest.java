package com.github.kirikakis.marvel.character.config;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MarvelConfig.class})
public class MarvelConfigTest {

    @Autowired
    private MarvelConfig marvelConfig;

    @Test
    public void getCharactersUrl() throws Exception {
        marvelConfig.setTimestamp(1l);
        marvelConfig.setCharactersUrl("http://example.com");
        marvelConfig.setPublicApiKey("1234");
        marvelConfig.setPrivateApiKey("abcd");
        marvelConfig.setCharactersLimit(31);
        String expectedUrl = "http://example.com?ts=1&apikey=1234&hash=ffd275c5130566a2916217b101f26150&limit=31&offset=0";
        assertEquals(expectedUrl, marvelConfig.getCharactersUrl(0));
    }
}