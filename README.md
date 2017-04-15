# HolaCMS 2017 (In Development)

## Requirements
* OpenJDK 1.8
* Verzeichnis unter /tmp/export/
* Open Port 9000

## Installation

### Export/Import
Zunächst benötigt der Server die Daten. Dazu ist eine Kopie der Holarse-Datenbank notwending. Diese muss auf einem erreichbaren MariaDB-Server (vorkonfiguriert ist localhost) installiert sein. 

Für den Export benötigt man noch ein weiteres Python3-Paket:

	sudo zypper in pymysql

Dann kann man die Daten aus der Datenbank für den Import vorbereiten mit:

	cd tools/importer/
	./importer.py

Konfigurieren kann man das Tool über die config.ini.

### Webapp
Den Webdienst kompiliert und startet man mit

	mvn clean package spring-boot:run

Vorrausgesetzt ist, dass Apache Maven als Buildtool installiert ist.

## Aufrufen
http://localhost:9000

## Diverses
Der Dienst erwartet die Dokumente unter /tmp/holarse/articles/
und /tmp/holarse/users/, Beispieldateien liegen in doc/examples/articles.
