-- to be installed prior to hibernate deployment

-- login erzeugen
CREATE ROLE holarse NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN NOREPLICATION;

-- sequence und reihenfolge festlegen
create schema web authorization holarse;
set search_path to web,public;

-- allgemeine hibernate sequence
create sequence hibernate_sequence;

-- revision sequence
create sequence revision_sequence;

create extension pgcrypto;