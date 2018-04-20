# HolaCMS 3 (In Development)

HolaCMS 3 wird die Basis für das neue HOLARSE 2018.

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
	PYTHONIOENCODING=utf8 ./importer.py

Konfigurieren kann man das Tool über die config.ini. Die alte Holarse-Datenbank ist latin1-kodiert.

## Import

Den Import der Dateien kann man über die REST-API durchführen. Beispiel:

    curl -v -H "Accept: application/xml" -H "Content-Type:application/xml" -X POST -d @99.xml http://localhost:8080/api/import/articles/


## Webapp

### Anforderungen
* Java 8 JDK
* Apache Maven

### Kompilieren
Die installierbare WAR-Datei erhält man durch das Bauen mit

	mvn clean package
	
im Hauptverzeichnis, wo sich auch die pom.xml befindet.

### Datenbank
Es muss eine PostgreSQL-Datenbank auf ```localhost``` vorliegen mit dem
Datenbanknamen ```holarse```, Benutzer ```holarse``` und Passwort ```geheim```.

### Aufrufen
Dazu gibt es das praktische Script

	./run_micro
	
Es lädt den Application-Server und den JDBC-Treiber herunter und startet eine Micro-Instanz. Möchte man die Webapplikation zuvor noch kompilieren startet man das Script mit

        ./run_micro compile
