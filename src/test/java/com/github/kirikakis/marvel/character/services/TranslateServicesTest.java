package com.github.kirikakis.marvel.character.services;

import com.google.cloud.translate.Detection;
import com.google.cloud.translate.Language;
import com.google.cloud.translate.Translate;
import com.google.cloud.translate.Translation;

import com.github.kirikakis.marvel.character.exceptions.LanguageCodeException;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.BDDMockito.given;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {TranslateService.class})
public class TranslateServicesTest {

    @MockBean
    private Translate translate;

    @Mock
    Detection detection;

    @Mock
    Translation translation;

    @Autowired
    private TranslateService translateService;

    private List<Language> languages;

    @Mock
    Language language;

    @Before
    public void setUpClass() {
        languages = new ArrayList<>();
        languages.add(language);
    }

    @Test
    public void translateText() throws Exception {
        String textToTranslate = "dog";
        String sourceLanguage = "en";
        String targetLanguage = "el";
        String translatedText = "σκύλος";

        given(translate.listSupportedLanguages()).willReturn(languages);
        given(language.getCode()).willReturn("el");
        given(translate.detect(textToTranslate)).willReturn(detection);
        given(detection.getLanguage()).willReturn(sourceLanguage);
        given(translate.translate(textToTranslate,
                Translate.TranslateOption.sourceLanguage(sourceLanguage),
                Translate.TranslateOption.targetLanguage(targetLanguage))).willReturn(translation);
        given(translation.getTranslatedText()).willReturn(translatedText);

        assertEquals(translatedText, translateService.translateText("dog", "el"));
    }

    @Test(expected = LanguageCodeException.class)
    public void translateTextThrowsLanguageCodeException() throws Exception {
        given(translate.listSupportedLanguages()).willReturn(languages);
        given(language.getCode()).willReturn("de");

        translateService.translateText("dog", "el");
    }
}