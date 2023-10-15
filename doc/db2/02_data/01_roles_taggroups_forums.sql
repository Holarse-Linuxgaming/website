-- 1. Roles
INSERT INTO roles (id, code, access_level) VALUES 
(nextval('hibernate_sequence'), 'ADMIN', 1),
(nextval('hibernate_sequence'), 'HOLARSE-CORE', 10),
(nextval('hibernate_sequence'), 'MODERATOR', 100),
(nextval('hibernate_sequence'), 'REPORTER', 250),
(nextval('hibernate_sequence'), 'TRUSTED_USER', 500);

-- 2. Tag groups
INSERT INTO taggroups(id, name) VALUES 
(nextval('hibernate_sequence'), 'LICENSE'),
(nextval('hibernate_sequence'), 'GENRE'),
(nextval('hibernate_sequence'), 'MULTIPLAYER'),
(nextval('hibernate_sequence'), 'STORE'),
(nextval('hibernate_sequence'), 'FRANCHISE'),
(nextval('hibernate_sequence'), 'COMPANY'),
(nextval('hibernate_sequence'), 'DEVELOPER'),
(nextval('hibernate_sequence'), 'PUBLISHER'),
(nextval('hibernate_sequence'), 'PORTER'),
(nextval('hibernate_sequence'), 'PLATFORM'),
(nextval('hibernate_sequence'), 'TECHNICAL'),
(nextval('hibernate_sequence'), 'ENGINE'),
(nextval('hibernate_sequence'), 'PACKAGEMANAGER');


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
INSERT INTO apiusers (id, login, rolename, token, active)
VALUES
    (nextval('hibernate_sequence'), 'dummy', 'API_IMPORT', 'ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940', true); -- u: dummy, p: geheim

INSERT INTO apiusers (id, login, rolename, token, active)
VALUES
    (nextval('hibernate_sequence'), 'dbl', 'API_DRÜCKBLICK', 'ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940', true); -- u: dbl, p: geheim

