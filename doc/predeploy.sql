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

-- partitioned table für zugriffsstatistiken
create table accesslog (id bigserial, nodeid bigint, visitorid varchar(255), campaignkeyword varchar(255), campaignname varchar(255), httpstatus int, ipaddress varchar(255), 
                        referer varchar(255), searchword varchar(255), url varchar(2083), useragent varchar(255), bot boolean default false, 
                        accessed timestamp with time zone not null default current_timestamp) partition by range(accessed);

create index on accesslog (accessed);

create table accesslog_2018 partition of accesslog for values from ('2018-01-01') to ('2019-01-01');
create table accesslog_2019 partition of accesslog for values from ('2019-01-01') to ('2020-01-01');
create table accesslog_2020 partition of accesslog for values from ('2020-01-01') to ('2021-01-01');
create table accesslog_2021 partition of accesslog for values from ('2021-01-01') to ('2022-01-01');
create table accesslog_2022 partition of accesslog for values from ('2022-01-01') to ('2023-01-01');
create table accesslog_2023 partition of accesslog for values from ('2023-01-01') to ('2024-01-01');
create table accesslog_2024 partition of accesslog for values from ('2024-01-01') to ('2025-01-01');

--insert into accesslog(accessed, campaignkeyword, campaignname, httpstatus, ipaddress, nodeid, referer, searchword, url
--                        useragent, visitorid, bot) select pv.created as accessed, campaignkeyword, campaignname, httpstatus, ipaddress,
--                        nodeid, referer, searchword, url, useragent, visitorid, false as bot from pv;