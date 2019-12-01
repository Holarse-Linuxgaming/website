Tomcat Application Server

Einrichtung ist sehr einfach, wenn ihr Netbeans verwendet. Ladet euch Tomcat von der Webseite als Zip-Archiv herunter:

    wget http://ftp.fau.de/apache/tomcat/tomcat-9/v9.0.29/bin/apache-tomcat-9.0.29.tar.gz

Entpacken und dann das JDBC-Archiv[2] hinterher kopieren:

    tar xfvz apache-tomcat-9.0.29.tar.gz
    cd apache-tomcat-9.0.29/lib
    wget https://jdbc.postgresql.org/download/postgresql-42.2.8.jar

Dann mit Netbeans über Services -> Servers -> Rechte Maus 'Add Server' und den Tomcat auswählen und auf das 
eben entpackte Verzeichnis zeigen lassen.

Dann kann man das Holarse-Archiv mit "Run" dort ausführen lassen.

--
[1]: https://tomcat.apache.org/download-90.cgi
[2]: https://jdbc.postgresql.org/download.html