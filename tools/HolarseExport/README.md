# Holarse Export

Tool zum Exportieren der Holarse 2008 Drupal 6-Datenbank in das Holarse XML-Exportformat.

## Aufruf
Gestartet werden kann der Export mit

    java -jar target/HolarseExport-1.0-SNAPSHOT-jar-with-dependencies.jar

Es erwartet die Holarse-MySQL-Datenbank mit den Zugangsdaten, die in ```src/main/java/de/holarse/tools/holarseexport/ExportConnection.java``` definiert sind.
Die Export-Dateien werden fest nach ```/tmp/export/``` geschrieben.