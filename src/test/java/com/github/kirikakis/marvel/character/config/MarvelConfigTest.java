package com.github.kirikakis.marvel.character.config;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.DigestUtils;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {MarvelConfig.class})
public class MarvelConfigTest {

    @BeforeClass
    public static void beforeClass() {
        System.setProperty("some.property", "<value>");
        System.setProperty("marvel.charactersUrl", "http://example.com");
        System.setProperty("marvel.publicApiKey", "1234");
        System.setProperty("marvel.privateApiKey", "abcd");
        System.setProperty("marvel.charactersLimit", "100");
    }

    // Optionally:
    @AfterClass
    public static void afterClass() {
        System.clearProperty("some.property");
        System.clearProperty("marvel.charactersUrl");
        System.clearProperty("marvel.publicApiKey");
        System.clearProperty("marvel.privateApiKey");
        System.clearProperty("marvel.charactersLimit");
    }

    @Autowired
    private MarvelConfig marvelConfig;

    @Test
    public void getCharactersUrl() throws Exception {
        assertTrue(
                marvelConfig.getCharactersUrl(0).equals(
                        "http://example.com?ts=" + marvelConfig.timestamp +
                        "&apikey=1234&hash=" + DigestUtils.md5DigestAsHex((marvelConfig.timestamp + marvelConfig.getPrivateApiKey()
                        + marvelConfig.getPublicApiKey()).getBytes()) + "&limit=100&offset=0")
        );
    }
}