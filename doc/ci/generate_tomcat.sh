#!/bin/bash
# Tomcat server
TOMCAT_VER="apache-tomcat-9.0.35"
TOMCATURL="https://mirror.netcologne.de/apache.org/tomcat/tomcat-9/v9.0.35/bin/$TOMCAT_VER.zip"
TOMCATSHA="2322c2032d3d22adb102b7b366f5eb6d50f159b8076de2160138b1bc24b3061080143014ad67d03d68919264d4246e02719f69c865ddd77bc0b4cbdbda475f5e"
# PostgreSQL JDBC
JDBCPOST_VER="postgresql-42.2.10.jar"
JDBCPOST_URL="https://jdbc.postgresql.org/download/$JDBCPOST_VER"
JDBCPOST_SHA="91638625fe7c95ce5199ed47cc6d2a9ad25c01f3f6fea4196116d304f21fcc330a2d8550eff66568690ba8bca52f253a6329c0a0e1c70c8bda155fdf9b750ee7"

if [ ! "$(find "$HOME/tomcat" -mindepth 1 -print -quit 2>/dev/null)" ] ;
then
	echo "Downloading and installing tomcat to $HOME/tomcat"
	rm -Rf "$HOME/tomcat"
	wget "$TOMCATURL" -O "/tmp/tomcat9.zip"
	# Verify the content
	echo "Verifying tomcat9.zip ..."
	if ! echo "$TOMCATSHA /tmp/tomcat9.zip" | sha512sum -c -;
	then
		echo "Checksum failed" >&2
		exit 1
	fi
	echo "Installing and configuring"
	unzip -q /tmp/tomcat9.zip -d "$HOME/"
	mv "$HOME/$TOMCAT_VER" "$HOME/tomcat"
	rm -R "$HOME/tomcat/webapps/ROOT/"
	echo "$PWD"
	ln -s "$PWD/target/holarseweb" "$HOME/tomcat/webapps/ROOT"
	chmod +x "$HOME/tomcat/bin/catalina.sh"
	sed -i 's/INFO/OFF/g' $HOME/tomcat/conf/logging.properties
	sed -i 's/FINE/OFF/g' $HOME/tomcat/conf/logging.properties

	# Setup Postgres JDBC for tomcat
	wget "$JDBCPOST_URL" -O "/tmp/$JDBCPOST_VER"
	if ! echo "$JDBCPOST_SHA /tmp/$JDBCPOST_VER" | sha512sum -c -;
	then
		echo "Checksum failed" >&2
		exit 1
	fi
	mv "/tmp/$JDBCPOST_VER" "$HOME/tomcat/lib/"
fi
echo "Done installing tomcat"
