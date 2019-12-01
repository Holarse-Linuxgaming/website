create user holarse with encrypted password 'holarse';
create database holarse with owner holarse;

-- Wichtig beim Ausführen mit dem postgres-Benutzer 
-- für dieses Script ist unbedingt auf die Holarse-Datenbank verbinden. Sonst 
-- landen die Schemata in der postgres-Datenbank.

create schema search authorization holarse;
create schema logging authorization holarse;

-- als postgres-admin ausführen:
CREATE EXTENSION unaccent schema search;
CREATE EXTENSION pg_trgm schema search;
create extension btree_gin schema search;
create extension pgcrypto;

-- allgemeine hibernate sequence
create sequence hibernate_sequence start with 1;
grant select, update on sequence hibernate_sequence to holarse;

-- revision sequence
create sequence revision_sequence start with 1000;
grant select, update on sequence revision_sequence to holarse;