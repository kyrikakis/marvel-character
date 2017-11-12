<img src="https://i.annihil.us/u/prod/misc/marvel.svg" width=200/>

# marvel-character

### Table of Contents

* [Overview](#overview)
* [Getting Started](#getting-started)
    * [Prerequisites](#prerequisites)
    * [Execution](#execution)
* [Using API](#using-api)
    * [GET Characters](#get-characters)
    * [GET Character by Id](#get-character-by-id)
* [Deployment](#deployment)
    * [Prerequisites on a Live Environment](#prerequisites-on-a-live-environment)
* [Configuring Application](#configuring-application)
    * [Configuration File](#configuration-file)
    * [NOTES](#notes)

## Overview
Getting your favorite character from MARVEL. Retrieves the whole list of characters during bootstrap using the Marvel [developer API](https://developer.marvel.com/docs) and saves it into an [H2 Database Engine](http://www.h2database.com/html/main.html) instance for later use.

## Getting Started
These instructions will get you a copy of the project up and running on your local machine for development and testing purposes. See deployment for notes on how to deploy the project on a live system.

### Prerequisites
* Java 1.8 JDK (for installation instructions see [here](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html))

### Execution
For building the source code, running the tests and launching the application, please navigate in the root folder of the project from your console and execute the following command providing your Marvel API keys and wait for the application to load all the characters from Marvel:

#### Unix
`./gradlew test run -Dmarvel.publicApiKey="{your_public_key}" -Dmarvel.privateApiKey="{your_private_key}"`

#### Windows
`gradle.bat test run -Dmarvel.publicApiKey="{your_public_key}" -Dmarvel.privateApiKey="{your_private_key}"`

## Using API

### GET Characters
`curl -X GET --header 'Accept: application/json' 'http://localhost:8080/characters'`

#### result:
`[ 1011334, 1017100, 1009144 ]`

### GET Character by Id
`curl -X GET --header 'Accept: application/json' 'http://localhost:8080/characters/{Id}'`

#### result:
```
{
  "id": 115,
  "name": "Black Bird",
  "description": "",
  "thumbnail": {
    "path": "http://i.annihil.us/u/prod/marvel/i/mg/b/40/image_not_available",
    "extension": "jpg"
  }
}
```

## Deployment
In order to deploy the application navigate in the root folder of the project from your console and execute the following command.

#### Unix
`./gradlew build`

#### Windows
`gradle.bat build`

Then copy **build/distributions/marvel-character.zip** and unzip in a folder on the target machine, navigate to the unziped folder and execute:

#### Unix
`./bin/marvel-character --marvel.publicApiKey="{your_public_key}" --marvel.privateApiKey="{your_private_key}"`

#### Windows
`bin/marvel-character.bat --marvel.publicApiKey="{your_public_key}" --marvel.privateApiKey="{your_private_key}"`

### Prerequisites on a Live Environment
* Java 1.8 JRE (for installation instructions see [here](https://docs.oracle.com/javase/8/docs/technotes/guides/install/install_overview.html))

## Configuring Application
Configuring the application is possible through the application.yml file, you can change the tomcat listening port and the Marvel API parameters:
```
### Configuration File
server:
  port: 8080

marvel:
  charactersUrl: "http://gateway.marvel.com/v1/public/characters"
  charactersLimit: 100
```

#### NOTES
* On the Development environment the file is under **src/main/resources/application.yml**
* On the Live environment the file is in the root path of the unzipped folder under **config/application.yml**