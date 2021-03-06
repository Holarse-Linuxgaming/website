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

-- 3. Table Generation
\echo '-- Generating basic tables --'
--- Logging
CREATE TABLE IF NOT EXISTS logging.accesslog (
    id bigserial,
    nodeid bigint,
    visitorid varchar(255),
    campaignkeyword varchar(255),
    campaignname varchar(255),
    httpstatus int,
    ipaddress varchar(255),
    referer varchar(255),
    searchword varchar(255),
    url varchar(2083),
    useragent varchar(255),
    bot boolean DEFAULT false,
    accessed timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
) PARTITION BY range(accessed);
ALTER TABLE logging.accesslog OWNER TO :dbUser;

---- Index for accesslog
CREATE INDEX IF NOT EXISTS accesslog_accessed_idx ON logging.accesslog (accessed);
CREATE INDEX IF NOT EXISTS accesslog_nodeid_accessed_idx ON logging.accesslog (nodeid, accessed);

---- Table data for logging (FIXME!)
CREATE TABLE logging.accesslog_2019 PARTITION OF logging.accesslog FOR VALUES FROM ('2019-01-01') TO ('2020-01-01');
CREATE TABLE logging.accesslog_2020 PARTITION OF logging.accesslog FOR VALUES FROM ('2020-01-01') TO ('2021-01-01');
CREATE TABLE logging.accesslog_2021 PARTITION OF logging.accesslog FOR VALUES FROM ('2021-01-01') TO ('2022-01-01');
CREATE TABLE logging.accesslog_2022 PARTITION OF logging.accesslog FOR VALUES FROM ('2022-01-01') TO ('2023-01-01');
CREATE TABLE logging.accesslog_2023 PARTITION OF logging.accesslog FOR VALUES FROM ('2023-01-01') TO ('2024-01-01');
CREATE TABLE logging.accesslog_2024 PARTITION OF logging.accesslog FOR VALUES FROM ('2024-01-01') TO ('2025-01-01');
CREATE TABLE logging.accesslog_2025 PARTITION OF logging.accesslog FOR VALUES FROM ('2025-01-01') TO ('2026-01-01');

-- End
\echo '----'
\echo 'Done, now "start" the web application to generate the other tables'
