# HolaCMS 2017 (In Development)

[![Build Status](https://travis-ci.org/Holarse-Linuxgaming/website.svg?branch=master)](https://travis-ci.org/Holarse-Linuxgaming/website)

## Requirements
* OpenJDK 1.8
* Verzeichnis unter /tmp/holarse/articles

## Installation
Kompilieren mit
```
mvn clean package
```
Den Server starten mit:
```
java -jar target/Holarse-1.0-SNAPSHOT.jar
```

## Aufrufen
http://localhost:9000

Der Dienst erwartet die Dokumente unter /tmp/holarse/articles/, Beispieldateien liegen in doc/examples/articles.
