Darstellung der Datenverarbeitung
=================================

# Dateisystem-Ebene
Lesen der Dateien von der Festplatte, wenn es keine höherliegende Instanz dieser Datei gibt. Die Daten sind je Node-Art (News, Article, Thread) in
ihrem Verzeichnis hinterlegt. In diesen Verzeichnissen gibt es für jede UID noch ein eigenes Unterverzeichnis. Dort werden weitere Unterordner
angelegt, z.B. "comments", "media", "revisions".

# Persistenz-Ebene
Einlesen der XML-Strukturen und Übertragen in POJOs.

# Caching der XML-Objekte
Die XML-Strukturen werden als Objekte gespeichert und gecacht im Speicher vorgehalten. (Cache Level 1)

# Aufbereiten der Objekte als Decorator
Die Objekte sind untereinander verknüpft. Die Relationen untereinander werden in einem Decorator-Objekt aufgelöst und die Daten aufbereitet. Diese Decorator-Objekte sind zur Verwendung als Datengrundlage für die spätere Darstellung auf der Oberfläche vorgesehen. Diese Teile werden ebenfalls wieder gecacht. (Cache Level 2)

# Render des Views
Das verknüpfte Decorator-Objekt kann gerendert werden in HTML. Diese Ansicht wird ebenfalls gecacht. (Cache Level 3)

## Speichern von Änderungen
Wenn eine Änderung z.B. in einer Überschrift vorgenommen wird, dann muss diese logischerweise gespeichert werden. Die Speicherung erfolgt in dem Objekt auf der Persistenz-Ebene. Beim Speichern wird der Cache Level 1 zerstört und beim nächsten Aufruf automatisch wieder aufgebaut. Dabei müssen die Caches, die das Objekt mit dieser UID enthalten, ebenfalls zerstört werden.

Wird eine Änderung in der Dateisystem-Ebene vorgenommen, muss der Persistenz-Dienst anwiesen werden, nach Änderungen zu gucken. Da sich die letzten Zeitstempel unterscheiden, liest dieser die XML-Datei ein und zerstört damit auch gleichzeitig den Cache für die XML-Objekte (Level 1), was sich dann wieder nach oben propagiert.


