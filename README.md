# HolaCMS 3 (In Development)

HolaCMS 3 wird die Basis für die neue Holarse-Webseite.

## Export-Tool
Das Export-Tool ermöglicht das Extrahieren der bestehenden Artikel, News, Kommentare und Forenbeiträge aus dem Drupal-Holarse. Es benötigt eine Installation der MySQL-Datenbank und den
dazugehörigen Dump (die alte Datenbank ist latin1).

Das Export-Tool liegt unter ```/tools/HolarseExport/``` und ist in Java geschrieben. 

## Import

Den Import der Dateien kann man über die REST-API durchführen. Es gibt zwei Scripte, die alle Export-Dateien automatisch an den Dummy-User übertragen. Sie liegen in ```/doc/import/```.

## Webapp

### Anforderungen
* Java 8 JDK, lieber Java 11
* Apache Maven
* PostgreSQL 11
* ElasticSearch 6

### Konfiguration
TODO

### Application Server
Siehe ``doc/openliberty/README.md``

### Datenbank
Die Datenbank per Repo einbinden, sofern das Betriebssystem schon PostgreSQL 11 hat. Ältere Versionen gehen jedoch auch.

Vor dem Deployment müssen die Kommandos in ```doc/predeploy.sql``` ausgeführt werden. Nach dem Deployment müssen einige Tabellen noch gefüllt werden. Dazu dann ```doc/schema.sql``` ausführen.


### ElasticSearch
ElasticSearch 6 und zur Konfiguration und Abfrage wird Kibana benötigt. Beide Pakete herunterladen und starten.

### Kompilieren
Die installierbare WAR-Datei erhält man durch das Bauen mit
    
    mvn clean package
    
im Hauptverzeichnis, wo sich auch die ```pom.xml``` befindet.

### Login
Zuerst einen Benutzer anlegen und diesen dann per SQL zum Admin erheben, indem in die Tabelle ```users_roles``` ein Eintrag mit beiden Foreign-Keys angelegt wird.