#!/bin/bash

WILDFLY="wildfly-10.0.0.Final"
MYSQL_J="mysql-connector-java-5.1.39"
MAVEN="apache-maven-3.3.9"

ADMIN_USER="admin"
ADMIN_PASS="geheim"

# vorraussetzungen prüfen
# openjdk testen
if [[ ! -x "$(command -v javac)" ]]; then
	echo "Java OpenJDK wird benötigt."
	exit 1
fi

JAVAC_VERSION=$(javac -version 2>&1)
if [[ ! $JAVAC_VERSION == javac\ 1.8* ]]; then
	echo "Java OpenJDK 1.8 wird benötigt."
	exit 1
fi	

# mysql testen
if [[ ! -x "$(command -v mysql)" ]]; then
	echo "Mysql-Client wird benötigt"
	exit 1
fi

# vorhandensein der tabelle testen
if [[ ! -d "/var/lib/mysql/holarse" ]]; then
	echo "Bitte die Holarse-Datenbank anlegen"
	exit 1
fi

# installation beginnen

rm -rf $WILDFLY
if [ ! -e "$WILDFLY.tar.gz" ]; then
	wget http://download.jboss.org/wildfly/10.0.0.Final/$WILDFLY.tar.gz
fi
tar xfz $WILDFLY.tar.gz

cd $WILDFLY
mkdir -p modules/system/layers/base/com/mysql/driver/main
cd ..

rm -rf $MYSQL_J
if [ ! -e "$MYSQL_J.tar.gz" ]; then
	wget http://dev.mysql.com/get/Downloads/Connector-J/$MYSQL_J.tar.gz
fi

tar xfz $MYSQL_J.tar.gz
cp $MYSQL_J/$MYSQL_J-bin.jar $WILDFLY/modules/system/layers/base/com/mysql/driver/main/
cp module.xml $WILDFLY/modules/system/layers/base/com/mysql/driver/main/

cd $WILDFLY
bin/standalone.sh &
sleep 5
bin/jboss-cli.sh --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql.driver,driver-class-name=com.mysql.jdbc.Driver)"
bin/jboss-cli.sh --connect --command="data-source add --name=holarseDS --jndi-name=java:/holarseDS --driver-name=mysql --connection-url=jdbc:mysql://localhost:3306/holarse --user-name=holarse --password=geheim"

bin/add-user.sh $ADMIN_USER $ADMIN_PASS
cd ..
echo "----------------------------------------------------------------------------------------------"
echo "Fertig, Login nun möglich unter http://localhost:9990 mit $ADMIN_USER und $ADMIN_PASS"
echo "----------------------------------------------------------------------------------------------"

echo "Installation Holarse"
rm -rf "$MAVEN"
if [ ! -e "$MAVEN-bin.tar.gz" ]; then
	wget "ftp://mirror.netcologne.de/apache.org/maven/maven-3/3.3.9/binaries/$MAVEN-bin.tar.gz"
fi
tar xfz $MAVEN-bin.tar.gz
cd ..
install/$MAVEN/bin/mvn clean package
install/$WILDFLY/bin/jboss-cli.sh --connect --command="deploy target/Holarse-1.0-SNAPSHOT.war"
