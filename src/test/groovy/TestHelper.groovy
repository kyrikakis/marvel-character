package com.github.kirikakis.marvel.character

class TestHelper {
    static def ReadFileFromResources(String fileName) {
        return new File('src/test/resources/' + fileName).text
    }
}
