# HolaCMS 2017 (In Development)

## Export-Tool

### Anforderungen
* python3
* python3-PyMySQL
* MySQL-Instanz mit der Holarse-Drupal-Datenbank

### Export/Import
Zunächst benötigt der Server die Daten. Dazu ist eine Kopie der Holarse-Datenbank notwending. Diese muss auf einem erreichbaren MariaDB-Server (vorkonfiguriert ist localhost) installiert sein. 

Für den Export benötigt man noch ein weiteres Python3-Paket:

	sudo zypper in python3-PyMySQL

Dann kann man die Daten aus der Datenbank für den Import vorbereiten mit:

	cd tools/importer/
	./importer.py

Konfigurieren kann man das Tool über die config.ini.

## Webapp

### Anforderungen
* Java 8 JDK
* Apache Maven

### Kompilieren
Die installierbare WAR-Datei erhält man durch das Bauen mit

	mvn clean package
	
im Hauptverzeichnis, wo sich auch die pom.xml befindet.

### Basisdaten
Der Dienst erwartet die vom Export-Tool erzeugten Dokumente unter ```/tmp/export/```
in den jeweiligen Unterzeichnissen ```articles```, ```news```, ```users``` und
```comments```.

### Datenbank
Es muss eine PostgreSQL-Datenbank auf ```localhost``` vorliegen mit dem
Datenbanknamen ```holarse```, Benutzer ```holarse``` und Passwort ```geheim```.

### Aufrufen
Dazu gibt es das praktische Script

	./run_micro
	
Es lädt den Application-Server und den JDBC-Treiber herunter und startet eine Micro-Instanz.
