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
* Java 8 JDK (Payara kann derzeit noch kein Java 11)
* Apache Maven
* PostgreSQL 11
* ElasticSearch 6

### Konfiguration
TODO

### Application Server
Payara kann kostenlos von https://www.payara.fish/software/downloads/ heruntergeladen werden. Das Archiv entpacken mit

    unzip payara-5.191.zip

Um den Zugriff auf die Datenbank zu ermöglichen, benötigt Payara noch die PostgreSQL-JDBC-Treiber. Diese können von https://jdbc.postgresql.org/download/postgresql-42.2.5.jar in das Glassfish-Verzeichnis ```glassfish/lib/``` 
heruntergeladen werden. Wurde der Treiber nachträglich hinzugefügt, muss Payara neugestartet werden, damit der Treiber automatisch gefunden werden kann.

Wenn die Datenbank bereits vorhanden ist, kann der Connection-Pool und die JNDI-Resource eingerichtet werden.

Aus dem Payara-Verzeichnis heraus:

    bin/asadmin create-jdbc-connection-pool --datasourceclassname org.postgresql.ds.PGConnectionPoolDataSource --restype javax.sql.DataSource --property serverName=holarse-data:user=holarse:password=geheim:port=5432:database=holarse holarsePool

Die Verbindung kann geprüft werden mit:

    bin/asadmin ping-connection-pool holarsePool

Dann die JDNI-Resource anlegen:

    bin/asadmin create-jdbc-resource --connectionpoolid holarsePool jdbc/holarse

Fertig.

### Datenbank
Die Datenbank per Repo einbinden, sofern das Betriebssystem schon PostgreSQL 11 hat. Ältere Versionen gehen jedoch auch.

Vor dem Deployment müssen die Kommandos in ```doc/predeploy.sql``` ausgeführt werden. Nach dem Deployment müssen einige Tabellen noch gefüllt werden. Dazu dann ```doc/schema.sql``` ausführen.


### ElasticSearch
ElasticSearch 6 und zur Konfiguration und Abfrage wird Kibana benötigt. Beide Pakete herunterladen und starten.

### Kompilieren
Die installierbare WAR-Datei erhält man durch das Bauen mit
    
    mvn clean package
    
im Hauptverzeichnis, wo sich auch die ```pom.xml``` befindet.

Wird Payara von Netbeans aus gestartet, muss in der domain1/domain.xml die Verweise auf grizzly-npn auskommentiert werden. Damit geht http/2 verloren, das benötigt man für die Entwicklung aber
vorerst nicht.

### Login
Zuerst einen Benutzer anlegen und diesen dann per SQL zum Admin erheben, indem in die Tabelle ```users_roles``` ein Eintrag mit beiden Foreign-Keys angelegt wird.