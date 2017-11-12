package com.github.kirikakis.marvel.character.config;

import com.google.cloud.translate.Translate;
import com.google.cloud.translate.TranslateOptions;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Configuration
@Component
public class TranslateConfig {

    @Value("${translate.apiKey}")
    private String KEY;

    @Bean
    public Translate getTranslateService() {
        // set key created via google cloud console
        return TranslateOptions.newBuilder().setApiKey(KEY).build().getService();
    }
}
