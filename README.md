# HolaCMS 3 (In Development)

HolaCMS 3 wird die Basis für die neue Holarse-Webseite.

## Export-Tool
Das Export-Tool ermöglicht das Extrahieren der bestehenden Artikel, News, Kommentare und Forenbeiträge aus dem Drupal-Holarse. Es benötigt eine Installation der MySQL-Datenbank und den
dazugehörigen Dump (die alte Datenbank ist latin1).

Das Export-Tool liegt unter ```/tools/HolarseExport/``` und ist in Java geschrieben. Es benötigt eine lokale MySQL-Installation mit der Holarse-Datenbank. Es exportiert automatisch alles nach ```/tmp/export/```. 

## Import

Den Import der Dateien kann man über die REST-API durchführen. Es gibt zwei Scripte, die alle Export-Dateien automatisch an den Dummy-User übertragen. Sie liegen in ```/doc/import/``` und erwarten die zu importierenden Daten in ```/tmp/export/```.

## Webapp

### Anforderungen
* Java 11 JDK
* Apache Maven 3.x
* PostgreSQL 13
* Apache Tomcat 9.x

### Konfiguration Tomcat
1.) Tomcat installieren

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

Dann nur noch Tomcat starten (`/bin/catalina.sh run`) und man ist zum Arbeiten bereit.

Kleiner Hinweis noch:

Wenn man am Source Code (Keine Template-/HTML-Dateien) arbeitet, muss man mittels *mvn compile*
diese bauen, damit Tomcat die dann "nachlädt".

Template-/HTML-Dateien können in ```target/holarseweb/``` bearbeitet werden (und direkt nachgeladen werden)
aber werden beim bauen wieder **sofort überschrieben**.

### Datenbank
Die Datenbank per Distro-Repo einbinden oder von dem Postgresql-bereitgestelltem Repository. Die Anleitungen finden sich in ```/doc/db/```.

Die Benutzer müssen vorab angelegt werden. Zudem ist in der ```postgresql.conf``` noch die Authentifizierung von md5 (Standard) auf 
```
password_encryption = scram-sha-256
```
zu ändern.

Dann kann die Datenbank und der Benutzer entweder über das Script oder manuell angelegt werden. Per Script:


    psql -h HOST -U postgres -d holarse -W -f 00_createdb.sql

oder manuell auf dem Datenbank-Server via

    su - postgres
    createrole holarse
    createdb -O holarse holarse

angelegt werden.

Dann folgt das nächste Script ebenfalls noch als postgres-Benutzer mit

    psql -h HOST -U postgres -d holarse -W -f 01_as_postgres.sql

Nun muss Holarse gebaut und auf dem Tomcat deployt werden. Hibernate erstellt dann automatisch aus den Entity-Definitionen
die nötigen Datenbankstrukturen. Danach geht es weiter mit:

    psql -h HOST -U holarse -d holarse -W -f 02_searchindex.sql 
    psql -h HOST -U holarse -d holarse -W -f 03_postdata.sql

An dieser Stelle ist Holarse eingerichtet. Die Daten können nun importiert werden. Danach müssen die Daten noch bereinigt werden mit dem Script:

    psql -h HOST -U holarse -d holarse -W -f 04_after_article_import.sql

Willkommen im HolaCMS 3-Testsystem!

### Kompilieren
Die installierbare WAR-Datei erhält man durch das Bauen mit
    
    mvn clean package
    
im Hauptverzeichnis, wo sich auch die ```pom.xml``` befindet. Beim ersten Durchlaufen werden die gesamten Abhängigkeiten von
Maven aufgelöst und heruntergeladen.

Ist Tomcat so konfiguriert, dass das ROOT-Verzeichnis auf das Target-Verzeichnis zeigt, dann sollte die Webseite bereits unter http://localhost:8080 angezeigt werden. Sonst kann man die Datei ```/target/holarseweb.war``` über
die Tomcat-Manager-Konsole installieren.

### Login
Zuerst einen Benutzer anlegen und diesen dann per SQL zum Admin erheben, indem in die Tabelle ```users_roles``` ein Eintrag mit beiden Foreign-Keys angelegt wird.
