package com.github.kirikakis.marvel.character.services;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;

import com.github.kirikakis.marvel.character.exceptions.LanguageCodeException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TranslateService {

    private Translate translateService;

    @Autowired
    public void setTranslateService(Translate translateService) {
        this.translateService = translateService;
    }

    public String translateText(String textToTranslate, String langISOCode) throws LanguageCodeException {
        isLanguageCodeExists(langISOCode);
        Detection detection = translateService.detect(textToTranslate);
        Translation translation = translateService.translate(textToTranslate,
                Translate.TranslateOption.sourceLanguage(detection.getLanguage()),
                Translate.TranslateOption.targetLanguage(langISOCode));

        return translation.getTranslatedText();
    }

    private void isLanguageCodeExists(String languageCode) throws LanguageCodeException {
        List<Language> languages = translateService.listSupportedLanguages();
        boolean found = false;
        for(Language language : languages) {
            if(language.getCode().equals(languageCode)) found = true;
        }
        if(!found) {
            throw new LanguageCodeException();
        }
    }
}
