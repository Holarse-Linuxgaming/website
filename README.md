# Holarse 2017 (In Development)

[![Build Status](https://travis-ci.org/Holarse-Linuxgaming/website.svg?branch=master)](https://travis-ci.org/Holarse-Linuxgaming/website)

## Installation

wget
http://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.tar.gz
unzip wildfly-10.0.0.Final.tar.gz

### Datenbankanbindung:

Damit die Datenbankanbindung funktioniert muss der Treiber und die Datasource zum Applicationserver hinzugefügt werden.
Dazu lädt man sich den mysql-connector von https://dev.mysql.com/downloads/connector/j/ herunter und befolgt die Schritte
wie sie hier https://docs.jboss.org/author/display/WFLY10/DataSource+configuration unter "Modify the JAR" beschrieben sind.
Danach startet man die Commandline von WildFly (jboss-cli.sh), verbindet sich (mit dem Befehl connect) und führt folgenden
Befehl aus: deploy <Pfad zur präparierten Jar>
Jetzt kann in der Admin-Oberfläche von Wildfly (Default unter localhost:9990) unter Configuration -> Subsystems -> Datasources ->
Non-XA die Datenbank hinzugefügt werden (vorausgesetzt man hat einen admin user eingerichtet).
