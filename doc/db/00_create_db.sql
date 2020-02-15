--------
-- This script can be skipped, if database and user should
-- be manual generated.
--------

-- Variables for the script
\set dbName holarse
\set dbUser holarse
\set dbPassword geheim


-- 1. Create DB and user
--- Drop database and user
\echo '-- Pre-Cleaning - Delete database and user if exists --'
DROP DATABASE IF EXISTS :dbName;
DROP ROLE IF EXISTS :dbUser;


--- Create User and DB
\echo '-- Create database' :'dbName' 'with user' :'dbUser' 'and password:' :'dbPassword' '--'
CREATE USER :dbUser WITH ENCRYPTED PASSWORD :'dbPassword';
CREATE DATABASE :dbName WITH OWNER :dbUser;


-- End
\echo '----'
\echo 'Done, now "start" the web application to generate the other tables'
