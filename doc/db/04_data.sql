-- rollen anlegen
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'ADMIN', 0);
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'HOLARSE-CORE', 10);
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'MODERATOR', 100);
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'REPORTER', 250);
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'TRUSTED_USER', 500);

-- Taggruppen
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'LICENSE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'GENRE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'MULTIPLAYER');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'STORE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'FRANCHISE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'COMPANY');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'DEVELOPER');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'PUBLISHER');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'PORTER');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'PLATFORM');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'TECHNICAL');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'ENGINE');

-- dummy apiuser, Passwort geheim
insert into apiusers (id, login, rolename, token) values (nextval('hibernate_sequence'), 'dummy', 'API_IMPORT', 'ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940'); -- geheim

-- foren
insert into forums(id, title, description, slug) values 
(nextval('hibernate_sequence'), 'Spieletreff', 'Zum regelmäßige Spieletreff sind alle Linuxspieler herzlich eingeladen!', 'spieletreff'), 
(nextval('hibernate_sequence'), 'Holarse Services', 'Fragen oder Anregungen zu unseren Gameservern?', 'holarse-services'), 
(nextval('hibernate_sequence'), 'Hardware', 'Falls die Hardware mal nicht will', 'hardware'), 
(nextval('hibernate_sequence'), 'Holarse', 'Fragen und Anregungen zu Holarse selbst', 'holarse'), 
(nextval('hibernate_sequence'), 'Spiele', 'Rund um Linuxspiele','spiele'), 
(nextval('hibernate_sequence'), 'Windows/Wine etc', 'Rund um Windows-Spiele, Spielen mit Wine, Codeweavers oder Proton','windows-wine-etc'), 
(nextval('hibernate_sequence'), 'Open Source', 'Fragen rund um FOSS','open-source'), 
(nextval('hibernate_sequence'), 'Off-Topic', 'Was sonst irgendwo passt', 'off-topic');

-- tags erweitern
update tags set use_count = (select count(1) from articles_tags where tags_id = id);

-- triggerfunktion zum autom. aktualisieren der use_counts
CREATE OR REPLACE FUNCTION update_tag_use_count() RETURNS TRIGGER AS $trg_tag_usecount$
   BEGIN
      update tags set use_count = (select count(1) from articles_tags where tags_id = new.tags_id) where id = new.tags_id;
      RETURN NEW;
   END;
$trg_tag_usecount$ LANGUAGE plpgsql;

CREATE TRIGGER trg_tag_usecount AFTER INSERT OR DELETE ON articles_tags
FOR EACH ROW EXECUTE PROCEDURE update_tag_use_count();

-- menüeinträge erzeugen
insert into menuitems(id, created, label, url, weight, parent_id)
values 
(nextval('hibernate_sequence'), now(), 'News', '/news', 0, null),
(nextval('hibernate_sequence'), now(), 'Spielefinder', '/finder', 1, null),
(nextval('hibernate_sequence'), now(), 'Server', '/finder?tags=dedicated%20server', 2, null),
(nextval('hibernate_sequence'), now(), 'Community', '/forum', 3, null),
(nextval('hibernate_sequence'), now(), 'Kategorien', '#', 4, null);

-- todo die gruppierungen für die Kategorien hier noch hinterlegen