package com.github.kirikakis.marvel.character

import java.nio.file.Path
import java.nio.file.Paths

class TestHelper {
    static def ReadJsonFileFromResources(String fileName) {
        return new File('src/test/resources/' + fileName).text
    }
}
