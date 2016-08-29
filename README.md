# Holarse 2017 (In Development)

[![Build Status](https://travis-ci.org/Holarse-Linuxgaming/website.svg?branch=master)](https://travis-ci.org/Holarse-Linuxgaming/website)

## Installation
Das Installationscript aufrufen unter 
```
 install/install_wildfly.sh
```
Es lädt Wildfly, den Mysql-Connector und Maven selbstständig herunter. Das Java-JDK OpenJDK 1.8 braucht man weiterhin.

## Konfiguration
Es wird eine Mysql-Datenbank namens "holarse" vorausgesetzt mit den Credentials: "holarse" und "geheim". Identisch sind auch die Logindaten für die Managementoberflächeunter http://localhost:9990/.
Eine Beispielseite kann unter http://localhost:8080/website/hello.html gesehen werden. Dann funktioniert alles.
