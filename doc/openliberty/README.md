# OpenLiberty

Anleitung zum Deployen von Holarse mit dem OpenLiberty Application Server.

## Systemvoraussetzungen
- OpenLiberty (doh)
- Java 8/9/10/11

## Anleitung

- Herunterladen von https://openliberty.io:

    wget https://public.dhe.ibm.com/ibmdl/export/pub/software/openliberty/runtime/release/2019-04-19_0642/openliberty-19.0.0.4.zip

- Entpacken mit 

    unzip openliberty-19.0.0.4.zip

- In das entstandene Verzeichnis wechseln

    cd wlp

- Einen Holarse-Server anlegen

    bin/server create holarse

- JDBC-Treiber herunterladen

    mkdir -p usr/servers/holarse/lib/
    wget https://jdbc.postgresql.org/download/postgresql-42.2.5.jar -P usr/servers/holarse/lib/

- Konfiguration eintragen
Die Datei server.xml ersetzen. Hierbei ist wichtig, dass in der Datei server.env der Konfigurationswert DBPASS gesetzt wird.
