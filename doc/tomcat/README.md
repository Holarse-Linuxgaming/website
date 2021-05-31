# Application Server Setup

## Apache Tomcat 9.x 
```
wget https://artfiles.org/apache.org/tomcat/tomcat-9/v9.0.46/bin/apache-tomcat-9.0.46.tar.gz
tar xfvz apache-tomcat-9.0.46.tar.gz
```

## PostgreSQL-JDBC-Treiber
```
wget https://jdbc.postgresql.org/download/postgresql-42.2.20.jar -O apache-tomcat-9.0.46/lib/postgresql-42.2.20.jar
```

## Konfiguration
### Benutzer
In der Datei `conf/tomcat-users.xml` im Abschnitt `<tomcat-users>` folgende Rolle und Benutzer anlegen:
```
<role rolename="tomcat"/>

<user password="geheim" roles="tomcat,manager-script,admin" username="admin"/>
```

### Resource
In der Datei `conf/server.xml` im Abschnitt `<GlobalNamingResources>` folgende Resource anlegen:

```
<Resource name="jdbc/holarse" 
 factory="org.apache.tomcat.jdbc.pool.DataSourceFactory"
 auth="Container"
 type="javax.sql.DataSource"
 username="holarse"
 password="geheim"
 url="jdbc:postgresql://luna/holarse"
 driverClassName="org.postgresql.Driver"
 initialSize="20"
 maxWaitMillis="15000"
 maxTotal="75"
 maxIdle="20"
 maxAge="7200000"
 testOnBorrow="true"
 validationQuery="select 1"
/>      
```

### JNDI-Resource
In der Datei `conf/context.xml` im Abschnitt `<Context>` die JNDI-Resource definieren:

```
<ResourceLink name="jdbc/holarse" global="jdbc/holarse" type="javax.sql.DataSource"/>
```

