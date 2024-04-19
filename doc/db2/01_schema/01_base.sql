-- Die Datenbank komplett säubern
drop schema public cascade;
create schema public;

-- Basis-Sequenz für alle Einträge anlegen
create sequence hibernate_sequence start with 1;
grant select, update on sequence hibernate_sequence to holarse;

-- Erweiterungen für die Suche
CREATE EXTENSION if not exists unaccent;
CREATE EXTENSION if not exists pg_trgm;
CREATE EXTENSION if not exists btree_gin;

-- Erweiterungen für das alte Passwort-Hashing (MD5)
CREATE EXTENSION if not exists pgcrypto;
