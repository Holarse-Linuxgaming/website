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

