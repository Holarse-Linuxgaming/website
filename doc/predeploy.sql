-- to be installed prior to hibernate deployment

-- login erzeugen
CREATE ROLE holarse NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN NOREPLICATION;

-- sequence und reihenfolge festlegen
--create schema web authorization holarse;
--set search_path to web,public;

-- allgemeine hibernate sequence
create sequence hibernate_sequence start with 1;

-- revision sequence
create sequence revision_sequence start with 1000;

create extension pgcrypto;
CREATE EXTENSION unaccent;
CREATE EXTENSION pg_trgm;