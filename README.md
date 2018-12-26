# HolaCMS 3 (In Development)

HolaCMS 3 wird die Basis für die neue Holarse-Webseite.

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
* Java 8 JDK (Payara kann derzeit noch kein Java 11)
* Apache Maven

### Kompilieren
Die installierbare WAR-Datei erhält man durch das Bauen mit

	mvn clean package
	
im Hauptverzeichnis, wo sich auch die pom.xml befindet.

### Datenbank
Der Application Server (Payara.fish) muss eine JDBC-Resource zur Verfügung stellen mit dem Namen jdbc/holarse. Um diese zu Erstellen wird der JDBC-Treiber 4.2 von http://jdbc.postgresql.org benötigt.

Vor dem Deployment müssen die Kommandos in ```doc/predeploy.sql``` ausgeführt werden.

Nach dem Deployment müssen einige Tabellen noch gefüllt werden. Dazu dann ```doc/schema.sql``` ausführen.

### Application Server
Wird Payara von Netbeans aus gestartet, muss in der domain1/domain.xml die Verweise auf grizzly-npn auskommentiert werden. Damit geht http/2 verloren, das benötigt man für die Entwicklung aber
vorerst nicht.

### Login
Zuerst einen Benutzer anlegen und diesen dann per SQL zum Admin erheben.