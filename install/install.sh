#!/bin/bash

APPSERVER="payara-4.1.1.163"
MYSQL_J="mysql-connector-java-5.1.39"
MAVEN="apache-maven-3.3.9"

########################################################
ADMIN_USER="admin"
ADMIN_PASS="geheim"

DB_USER="holarse"
DB_PASS="geheim"
########################################################

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
	echo "Bitte die Holarse-Datenbank anlegen mit mysql -u root -p -e \"create database holarse\" und mysql -u root -p -e \"grant all privileges on holarse.* to $DB_USER@localhost identified by '$DB_PASS';\""
	exit 1
fi

# installation beginnen
rm -rf $APPSERVER
if [ ! -e "$APPSERVER.zip" ]; then
    wget "https://s3-eu-west-1.amazonaws.com/payara.co/Payara+Downloads/Payara+4.1.1.163/payara-4.1.1.163.zip"
fi
unzip $APPSERVER.zip

rm -rf $MYSQL_J
if [ ! -e "$MYSQL_J.tar.gz" ]; then
	wget http://dev.mysql.com/get/Downloads/Connector-J/$MYSQL_J.tar.gz
fi

tar xfz $MYSQL_J.tar.gz
cp $MYSQL_J/$MYSQL_J-bin.jar payara41/glassfish/lib/

cd payara41
# connection pool anlegen
bin/asadmin create-jdbc-connection-pool --datasourceclassname com.mysql.jdbc.jdbc2.optional.MysqlConnectionPoolDataSource --restype javax.sql.DataSource --ping=true --isconnectvalidatereq=true --validationmethod=auto-commit --property user=$DB_USER:password=$DB_PASS:databaseName=holarse:serverName=localhost:port=3306 holarsePool

bin/asadmin ping-connection-pool holarsePool

# jndi-resource anlegen
bin/asadmin create-jdbc-resource --connectionpoolid holarsePool jdbc/holarse

cd ..
echo "Installation Holarse"
rm -rf "$MAVEN"
if [ ! -e "$MAVEN-bin.tar.gz" ]; then
	wget "ftp://mirror.netcologne.de/apache.org/maven/maven-3/3.3.9/binaries/$MAVEN-bin.tar.gz"
fi
tar xfz $MAVEN-bin.tar.gz
cd ..
install/$MAVEN/bin/mvn clean package
install/payara41/bin/asadmin deploy --name holarse target/Holarse-1.0-SNAPSHOT.war
