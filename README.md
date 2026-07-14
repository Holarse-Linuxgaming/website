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
* Java 25
* Docker, docker-compose, make, mise
* Containerisiert: Apache Artemis, PostgreSQL, caddy, mailpit

### Setup
Der Aufruf von 
```sh
make setup
```
wird die nötigen Tools via mise und yarn installieren.

### Build
Der Build der Applikation und das generieren der CSS-Dateien wird mit
```sh
make build
```
ausgeführt.

### Container-Deployment
Die Volumes für PostgreSQL und AMQ werden automatisch angelegt.

Dnd die Containerbande starten mit
```bash
make up
```

Der Start der Spring Boot-Applikation führt auch automatisch die Datenbankmigration aus.

### Kompilieren
Ein Neukompilieren und ein Restart der App wird durch
```sh
make rebuild
```
erreicht.

### Apache Artemis Message-Queue
Die Message-Queue ist unter http://queue.holarse.localhost erreichbar (Sonst Port 8161).

### Datenbank
Die Datenbank ist unter http://db.holarse.localhost erreichbar.

### Mails
Der Mailcatcher ist unter http://mail.holarse.localhost erreichbar (Sonst Port 8025).

### S3-Storage
Die Storage-Console (garage) ist unter http://s3.holarse.localhost erreichbar (sonst Port 3909)

### Webseite
Die Webseite ist unter http://www.holarse.localhost erreichbar (sonst http://localhost:8080/holarseweb)

### Datenbank
Die Datenbank ist per docker compose initialisiert. Die Datenbankscripte werden über Flyway migriert.

### Login
Zuerst einen Benutzer über die Oberfläche registrieren und diesen dann per SQL zum Admin erheben:

```sh
tools/scripts/make_user_admin.sh DEINBENUTZERNAME
```

# Ende
Willkommen im HolaCMS 3-Testsystem!