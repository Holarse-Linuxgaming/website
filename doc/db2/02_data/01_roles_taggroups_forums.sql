-- 1. Roles
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'ADMIN', 0);
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'HOLARSE-CORE', 10);
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'MODERATOR', 100);
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'REPORTER', 250);
INSERT INTO roles (id, code, clearancelevel) VALUES (nextval('hibernate_sequence'), 'TRUSTED_USER', 500);

-- 2. Tag groups
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


-- 3. Forums
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

-- 5. APIUser
INSERT INTO apiusers (id, login, rolename, token)
VALUES
    (nextval('hibernate_sequence'), 'dummy', 'API_IMPORT', 'ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940'); -- u: dummy, p: geheim

