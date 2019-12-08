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


### Dev-Mode

1.) Tomcat und Postgres installieren

Siehe /doc/tomcat/README.txt

2.) Tomcat anpassen

Dazu im Tomcat-Ordner in `conf/context.xml` einfügen, im "Context"-Block:

`<Context>` wird zu `<Context reloadable="true">`

Damit lädt Tomcat die Anwendung neu, wenn sie gebaut wurde.

Nun wird noch ein Symlink in *webapps* erstellt.
Falls der Ordner `ROOT` in webapps existiert, wird dieser dann gelöscht.

Danach wird der target Ordner von diesen Repository verlinkt, damit wir darüber
dann "live" arbeiten können.

`ln -s ~/website/target/holarseweb/ tomcat/webapps/ROOT`

Dann nur noch tomcat starten (`/bin/catalina.sh run`) und man ist zum Arbeiten bereit.

Kleiner Hinweis noch:

Wenn man am Source Code (Keine Template-/HTML-Dateien) arbeitet, muss man mittels *mvn compile*
diese bauen, damit tomcat die dann "nachlädt".

Template-/HTML-Dateien können in target/holarseweb/ bearbeitet werden (und direkt nachgeladen werden)
aber werden beim bauen wieder **sofort überschrieben**.


### Login
Zuerst einen Benutzer anlegen und diesen dann per SQL zum Admin erheben, indem in die Tabelle ```users_roles``` ein Eintrag mit beiden Foreign-Keys angelegt wird.
