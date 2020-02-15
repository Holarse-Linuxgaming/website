--------
-- This script can be run by an admin user (e.g. postgres)
-- or the holarse user.
-- It is important, that 03_schemadata.sql has been
-- imported before using this script
--------

-- Variables for the script
\set dbName holarse
\set dbUser holarse
\set apiUser dummy
--- API Password: geheim
\set apiPassword ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940


-- 1. Set the DB to holarse
\connect -reuse-previous=on :dbName;


-- 2. Roles
\echo '-- Insert roles --'
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'ADMIN', 0);
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'HOLARSE-CORE', 10);
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'MODERATOR', 100);
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'REPORTER', 250);
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'TRUSTED_USER', 500);

-- 3. Tag groups
\echo '-- Insert tag groups --'
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'LICENSE');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'GENRE');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'MULTIPLAYER');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'STORE');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'FRANCHISE');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'COMPANY');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'DEVELOPER');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'PUBLISHER');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'PORTER');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'PLATFORM');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'TECHNICAL');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'ENGINE');
INSERT INTO taggroups(id, name) VALUES (nextval('hibernate_sequence'), 'PACKAGEMANAGER');


-- 4. Forums
\echo '-- Insert forums --'
INSERT INTO forums(id, title, description, slug)
VALUES 
    (nextval('hibernate_sequence'), 'Spieletreff', 'Zum regelmäßige Spieletreff sind alle Linuxspieler herzlich eingeladen!', 'spieletreff'), 
    (nextval('hibernate_sequence'), 'Holarse Services', 'Fragen oder Anregungen zu unseren Gameservern?', 'holarse-services'), 
    (nextval('hibernate_sequence'), 'Hardware', 'Falls die Hardware mal nicht will', 'hardware'), 
    (nextval('hibernate_sequence'), 'Holarse', 'Fragen und Anregungen zu Holarse selbst', 'holarse'), 
    (nextval('hibernate_sequence'), 'Spiele', 'Rund um Linuxspiele','spiele'), 
    (nextval('hibernate_sequence'), 'Windows/Wine etc', 'Rund um Windows-Spiele, Spielen mit Wine, Codeweavers oder Proton','windows-wine-etc'), 
    (nextval('hibernate_sequence'), 'Open Source', 'Fragen rund um FOSS','open-source'), 
    (nextval('hibernate_sequence'), 'Off-Topic', 'Was sonst irgendwo passt', 'off-topic');


-- 5. Menu
\echo '-- Insert menu --'
INSERT INTO menuitems(id, created, label, url, weight, parent_id)
VALUES
    (nextval('hibernate_sequence'), now(), 'News', '/news', 0, null),
    (nextval('hibernate_sequence'), now(), 'Spielefinder', '/finder', 1, null),
    (nextval('hibernate_sequence'), now(), 'Server', '/finder?tags=dedicated%20server', 2, null),
    (nextval('hibernate_sequence'), now(), 'Community', '/forum', 3, null),
    (nextval('hibernate_sequence'), now(), 'Kategorien', '#', 4, null);


-- 6. APIUser
\echo '-- Create API user into DB --'
INSERT INTO apiusers (id, login, rolename, token)
VALUES
    (nextval('hibernate_sequence'), :'apiUser', 'API_IMPORT', :'apiPassword');


-- End
\echo '----'
\echo 'Done!'
