-- rollen anlegen
insert into web.roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'ADMIN', 0);
insert into web.roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'HOLARSE-CORE', 10);
insert into web.roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'MODERATOR', 100);
insert into web.roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'REPORTER', 250);
insert into web.roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'TRUSTED_USER', 500);

insert into web.taggroups(id, name) values (nextval('hibernate_sequence'), 'LICENSE');
insert into web.taggroups(id, name) values (nextval('hibernate_sequence'), 'GENRE');
insert into web.taggroups(id, name) values (nextval('hibernate_sequence'), 'MULTIPLAYER');
insert into web.taggroups(id, name) values (nextval('hibernate_sequence'), 'STORE');
insert into web.taggroups(id, name) values (nextval('hibernate_sequence'), 'FRANCHISE');
insert into web.taggroups(id, name) values (nextval('hibernate_sequence'), 'COMPANY');
insert into web.taggroups(id, name) values (nextval('hibernate_sequence'), 'DEVELOPER');
insert into web.taggroups(id, name) values (nextval('hibernate_sequence'), 'PUBLISHER');

-- dummy apiuser, Passwort geheim
insert into web.apiusers (id, login, rolename, token) values (nextval('hibernate_sequence'), 'dummy', 'API_INTERNAL', 'ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940'); -- geheim

-- foren
insert into web.forums(id, title, description, slug) values 
(nextval('web.hibernate_sequence'), 'Spieletreff', 'Zum regelmäßige Spieletreff sind alle Linuxspieler herzlich eingeladen!', 'spieletreff'), 
(nextval('web.hibernate_sequence'), 'Holarse Services', 'Fragen oder Anregungen zu unseren Gameservern?', 'holarse-services'), 
(nextval('web.hibernate_sequence'), 'Hardware', 'Falls die Hardware mal nicht will', 'hardware'), 
(nextval('web.hibernate_sequence'), 'Holarse', 'Fragen und Anregungen zu Holarse selbst', 'holarse'), 
(nextval('web.hibernate_sequence'), 'Spiele', 'Rund um Linuxspiele','spiele'), 
(nextval('web.hibernate_sequence'), 'Windows/Wine etc', 'Rund um Windows-Spiele, Spielen mit Wine, Codeweavers oder Proton','windows-wine-etc'), 
(nextval('web.hibernate_sequence'), 'Open Source', 'Fragen rund um FOSS','open-source'), 
(nextval('web.hibernate_sequence'), 'Off-Topic', 'Was sonst irgendwo passt', 'off-topic');