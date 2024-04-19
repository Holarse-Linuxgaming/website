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
* Java 21
* Docker, docker-compose
* Apache Maven 3.x
* Containerisiert: Apache Tomcat 10, Apache Artemis, PostgreSQL 16

### Container-Deployment
Zuerst die Volumes für Postgres und AMQ anlegen
```bash
docker volume create website_my_pgdata
docker volume create amq_journal_data
```

dann am Holarse Container-Repository anmelden
```bash
git login git.holarse.de
```

Jetzt noch die Datenbank initial befüllen mit dem Script
```bash
./setup_db_sql.sh
```

und die Containerbande starten mit
```bash
docker-compose -f doc/docker-compose.yml up
```

### Apache Artemis Message-Queue
Die Message-Queue ist unter http://localhost:8161 erreichbar.

### Datenbank
Die Datenbank ist unter http://localhost:5432 erreichbar.

### Webseite

Die Webseite ist unter http://localhost:8080/holarseweb/ erreichbar.

#### Manuelles Deployment
Die Datenbank per Distro-Repo einbinden oder von dem Postgresql-bereitgestelltem Repository. Die Anleitungen finden sich in ```/doc/db/```.

Die Benutzer müssen vorab angelegt werden. Zudem ist in der ```postgresql.conf``` noch die Authentifizierung von md5 (Standard) auf 
```
password_encryption = scram-sha-256
```
zu ändern.

Dann kann die Datenbank und der Benutzer manuell auf dem Datenbank-Server via

    su - postgres
    createrole holarse
    createdb -O holarse holarse

angelegt werden.

Dann folgt das Schema-Script unter ```doc/db2/01_schema``` ebenfalls noch als postgres-Benutzer mit

    psql -h HOST -U postgres -d holarse -W -f 01_*.sql
 
Dann die weiteren Dateien aufsteigend in den Nummern, bis es keine mehr gibt.

Danach können einige Daten importiert werden. Diese sind unter ```doc/db2/02_data```.

    
An dieser Stelle ist Holarse eingerichtet. Die Daten können nun importiert werden. Danach müssen die Daten noch bereinigt werden mit dem Script:

    psql -h HOST -U holarse -d holarse -W -f doc/db/04_after_article_import.sql

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
