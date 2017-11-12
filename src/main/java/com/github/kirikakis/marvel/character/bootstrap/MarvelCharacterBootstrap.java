package com.github.kirikakis.marvel.character.bootstrap;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.kirikakis.marvel.character.MarvelCharacterRepository;
import com.github.kirikakis.marvel.character.config.MarvelConfig;
import com.github.kirikakis.marvel.character.model.MarvelCharacter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.util.List;

@Component
public class MarvelCharacterBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    private MarvelConfig marvelConfig;
    private MarvelCharacterRepository marvelCharacterRepository;
    private RestTemplate restTemplate;

    @Autowired
    public void setMarvelCharacterRepository(MarvelCharacterRepository marvelCharacterRepository) {
        this.marvelCharacterRepository = marvelCharacterRepository;
    }

    @Autowired
    public void setRestTemplate(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Autowired
    public void setMarvelConfig(MarvelConfig marvelConfig) {
        this.marvelConfig = marvelConfig;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            loadAllCharactersFromMarvelToMemory();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    void loadAllCharactersFromMarvelToMemory() throws IOException {
        int count = 0;
        int total = 0;
        do {
            marvelConfig.setTimestamp(System.currentTimeMillis());
            String result = restTemplate.getForObject(marvelConfig.getCharactersUrl(count), String.class);
            System.out.println(result);
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonData = objectMapper.readTree(result).get("data");
            total = jsonData.get("total").asInt();
            count += jsonData.get("count").asInt();
            JsonNode jsonCharacters = jsonData.get("results");
            TypeReference<List<MarvelCharacter>> listType = new TypeReference<List<MarvelCharacter>>() {};
            List<MarvelCharacter> marvelCharacters = objectMapper.convertValue(jsonCharacters, listType);
            marvelCharacterRepository.save(marvelCharacters);
        }
        while (count < total);
    }
}
