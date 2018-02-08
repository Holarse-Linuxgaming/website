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

### Aufrufen
Hierfür benötigt man einen Application Server. Der Payara-Micro-Server ist
eine einfache Jar, die heruntergeladen werden kann und einen Application
Server ohne Installation und Konfiguration bereitstellt.

Den Payara Micro erhält man von https://www.payara.fish/downloads. 

Er wird dann wie folgt ausgeführt (aus dem Verzeichnis, wo sich auch die
pom.xml befindet):

	java -jar payara-micro-4.1.2.173.jar --nocluster --disablephonehome --deploy target/HolarseWeb-1.0-SNAPSHOT.war

Die Seite ist dann erreichbar unter ```http://localhost:8080```.
