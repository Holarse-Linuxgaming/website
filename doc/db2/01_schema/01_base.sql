-- Basis-Sequenz für alle Einträge anlegen
create sequence hibernate_sequence start with 1;
grant select, update on sequence  hibernate_sequence to holarse;

-- Erweiterungen für die Suche
CREATE EXTENSION unaccent;
CREATE EXTENSION pg_trgm;
CREATE EXTENSION btree_gin;

-- Erweiterungen für das alte Passwort-Hashing (MD5)
CREATE EXTENSION pgcrypto;
