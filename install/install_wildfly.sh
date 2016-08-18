#!/bin/bash

wget http://download.jboss.org/wildfly/10.0.0.Final/wildfly-10.0.0.Final.tar.gz
tar xfvz wildfly-10.0.0.Final.tar.gz
cd wildfly-10.0.0.Final
mkdir -p modules/system/layers/base/com/mysql/main
wget http://dev.mysql.com/get/Downloads/Connector-J/mysql-connector-java-5.1.39.tar.gz -P /tmp
tar xfvz /tmp/mysql-connector-java-5.1.39.tar.gz -C /tmp
cp /tmp/mysql-connector-java-5.1.39/mysql-connector-java-5.1.39-bin.jar modules/system/layers/base/com/mysql/main/
cd ..
cp module.xml wildfly-10.0.0.Final/modules/system/layers/base/com/mysql/main/

bin/jboss-cli.sh --connect --command="/subsystem=datasources/jdbc-driver=mysql:add(driver-name=mysql,driver-module-name=com.mysql.jdbc"
bin/jboss-cli.sh --connect --command="data-source add --name=holarseDS --jndi-name=java:/holarseDS --driver-name=mysql --connection-url=jdbc:mysql://localhost:3306/holarse --user-name=holarse --password=geheim"
