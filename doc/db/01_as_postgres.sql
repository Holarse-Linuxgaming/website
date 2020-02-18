--------
-- This script needs to be run as postgres or admin user.
-- The password should be changed here to something secure
--------

-- Variables for the script
\set dbUser holarse


-- 1. Schema creation and extensions
--- Schema
\echo '-- Create schemas and extensions --'
CREATE SCHEMA search AUTHORIZATION :dbUser;
CREATE SCHEMA logging AUTHORIZATION :dbUser;
--- Extensions for the schemas
CREATE EXTENSION unaccent;
CREATE EXTENSION pg_trgm;
CREATE EXTENSION btree_gin;
CREATE EXTENSION pgcrypto;


-- 2. Sequence Generation
\echo '-- Create sequences for hibernate --'
--- hibernate_sequence
CREATE SEQUENCE hibernate_sequence START WITH 1;
GRANT SELECT, UPDATE ON sequence hibernate_sequence TO :dbUser;
--- revision sequence
CREATE SEQUENCE revision_sequence START WITH 1000;
GRANT SELECT, UPDATE ON sequence revision_sequence TO :dbUser;

---- Nodetype
CREATE TYPE public.nodetype AS ENUM (
    'article',
    'news'
);
ALTER TYPE public.nodetype OWNER TO :dbUser;

---- Suggestiontype
CREATE TYPE public.suggestiontype AS ENUM (
    'title',
     'tag'
);
ALTER TYPE public.suggestiontype OWNER TO :dbUser;


-- End
\echo '----'
\echo 'Done, now "start" the web application to generate the other tables'
