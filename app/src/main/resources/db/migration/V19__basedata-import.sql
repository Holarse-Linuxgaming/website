-- 1. Roles
INSERT INTO roles (id, code, access_level) VALUES 
(nextval('hibernate_sequence'), 'ADMIN', 1),
(nextval('hibernate_sequence'), 'HOLARSE-CORE', 10),
(nextval('hibernate_sequence'), 'MODERATOR', 100),
(nextval('hibernate_sequence'), 'REPORTER', 250),
(nextval('hibernate_sequence'), 'TRUSTED_USER', 500);

-- 2. Tag groups
INSERT INTO taggroups(id, name, code) VALUES
(nextval('hibernate_sequence'), 'Lizenz', 'LICENSE'),
(nextval('hibernate_sequence'), 'Genre', 'GENRE'),
(nextval('hibernate_sequence'), 'Multiplayer', 'MULTIPLAYER'),
(nextval('hibernate_sequence'), 'Händler', 'STORE'),
(nextval('hibernate_sequence'), 'Franchise', 'FRANCHISE'),
(nextval('hibernate_sequence'), 'Firma', 'COMPANY'),
(nextval('hibernate_sequence'), 'Entwickler', 'DEVELOPER'),
(nextval('hibernate_sequence'), 'Publisher', 'PUBLISHER'),
(nextval('hibernate_sequence'), 'Portierer', 'PORTER'),
(nextval('hibernate_sequence'), 'Plattform', 'PLATFORM'),
(nextval('hibernate_sequence'), 'Technisch', 'TECHNICAL'),
(nextval('hibernate_sequence'), 'Engine', 'ENGINE'),
(nextval('hibernate_sequence'), 'Paketmanager', 'PACKAGEMANAGER'),
(nextval('hibernate_sequence'), 'Sonstiges', 'OTHER');


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

-- 6. attachments
insert into attachment_groups (code, label)
values
('website', 'Webseiten'),
('wine', 'wine'),
('shop', 'Shops'),
('video', 'Videos'),
('image', 'Bilder/Screenshots'),
('file', 'Dateien/Anhänge'),
('repo', 'Repos');

insert into attachment_types (code, label, attachment_group_id, datatype) values
('link', 'Link', (select id from attachment_groups where code = 'website'), 'url'),

('winehq', 'WineHQ', (select id from attachment_groups where code = 'wine'), 'url'),
('protondb', 'ProtonDB', (select id from attachment_groups where code = 'wine'), 'url'),
('protonofficial', 'Proton Official', (select id from attachment_groups where code = 'wine'), 'url'),
('crossoverdb', 'CrossOver', (select id from attachment_groups where code = 'wine'), 'url'),

('steam', 'Steam', (select id from attachment_groups where code = 'shop'), 'url'),
('humble', 'HumbleStore', (select id from attachment_groups where code = 'shop'), 'url'),
('gog', 'GOG', (select id from attachment_groups where code = 'shop'), 'url'),
('ownshop', 'Hersteller', (select id from attachment_groups where code = 'shop'), 'url'),
('itch', 'Itch', (select id from attachment_groups where code = 'shop'), 'url'),

('youtube', 'YouTube', (select id from attachment_groups where code = 'video'), 'url'),
('youtube-channel', 'YouTube-Kanal', (select id from attachment_groups where code = 'video'), 'url'),
('twitch', 'Twitch', (select id from attachment_groups where code = 'video'), 'url'),

('screenshot', 'Screenshot', (select id from attachment_groups where code = 'image'), 'storage'),

('file', 'Dateianhang', (select id from attachment_groups where code = 'file'), 'storage'),

('appimage', 'AppImage', (select id from attachment_groups where code = 'repo'), 'url'),
('flatpak', 'Flatpak', (select id from attachment_groups where code = 'repo'), 'url'),
('snap', 'Snap', (select id from attachment_groups where code = 'repo'), 'url');

insert into news_categories(name, active, weight) values ('News', true, 70), ('Drückblick', true, 50), ('Kurznews', true, 30), ('Review', true, 20), ('Artikel', true, 10), ('In eigener Sache', true, 1);