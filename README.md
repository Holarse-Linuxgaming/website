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

## Struktur
### Ebene 1 (file)
Vom Dateisystem aus werden die XML-Dokumente als Objekte eingelesen. Der
PersistenceService kümmert sich auch um das Zurückspeichern der
Objekte. Hierbei liegt ein Cache vor, der unnötige I/O-Operationen auf der
Hardware verhindert.

### Ebene 2 (reference)
Innerhalb der XML-Strukturen wird es Verzweigungen auf andere XML-Objekte
geben (bspw. eine Benutzerreferenz eines Artikels auf ein Benutzerobjekt). Der
IntegrityService kümmert sich um das Auflösen der Referenzen zu den jeweiligen
Objekten. Diese werden dann als ein vollreferenziertes Objekt zur Verfügung
gestellt.
Diese werden ebenfalls in einem Cache vollaufgelöst zur Verfügung
gestellt. Der Cache hier muss allerdings mit unterstützen, dass gewisse
Referenzen aktualisiert worden sind. Wie das gelöst werden kann, ist noch
nicht geklärt. (TODO)

### Ebene 3 (decorator)
Hier werden die Rohdaten aufgearbeitet und für die Präsentation
vorbereitet. Objekte der Ebene 3 werden auch direkt an den View
übergeben. Diese enthalten z.B. das in HTML umgewandelte Markdown und werden
entsprechend ebenfalls gecacht. 
## Trennung BL/Representation
Um beim Cachen nicht unnötig Buisiness Logic mitzuspeichern, müssen die
Datenobjekte auch nur Daten und keine Logik enthalten. Entsprechend müssen
alle Logikteile in Dienste ausgelagert werden, die auf den Datenobjekten arbeiten.
## Cache
Der Cache wird von unten (Ebene 1) nach oben (Ebene 3) gesteuert. Wenn ein
Objekt auf Ebene 1 geändert wird, dann gibt es den weiteren Ebenen nach oben
Bescheid, die wiederrum ihren Cache-Eintrag dann vernichten.

Der Cache basiert auf JCache (JSR 107).
