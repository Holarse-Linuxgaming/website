-- zuletzt ausführen!

-- tag umbenennen
update articles_tags set tags_id = (select id from tags where name = 'Kommerziell') where tags_id = (select id from tags where name = 'Kommerztiell');
update articles_tags set tags_id = (select id from tags where name = 'Kommerziell') where tags_id = (select id from tags where name = 'Kommerzille');
update tags set name = 'infinite runner' where name = 'infinte runner';
update tags set name = 'Mehrspieler' where name = 'mehrspieler';

-- tag anderweitig zuweisen
update articles_tags set tags_id = (select id from tags where name = '32bit') where tags_id = (select id from tags where name = 'native_x86');
update articles_tags set tags_id = (select id from tags where name = 'amd64') where tags_id = (select id from tags where name = 'native_x86_64');
update articles_tags set tags_id = (select id from tags where name = 'einstellt') where tags_id = (select id from tags where name = 'einstellt. eingestampft');
update articles_tags set tags_id = (select id from tags where name = 'Flatpack') where tags_id = (select id from tags where name = 'Flatpak');
update articles_tags set tags_id = (select id from tags where name = 'Gamepad') where tags_id = (select id from tags where name = 'Gampad');
update articles_tags set tags_id = (select id from tags where name = 'kein LAN') where tags_id = (select id from tags where name = 'keinLAN');
update articles_tags set tags_id = (select id from tags where name = 'Mod') where tags_id = (select id from tags where name = 'Mods');
update articles_tags set tags_id = (select id from tags where name = 'Steam VR') where tags_id = (select id from tags where name = 'SteamVR');
update articles_tags set tags_id = (select id from tags where name = 'split-screen') where tags_id = (select id from tags where name = 'splitscreen');

--update articles_tags set tags_id = (select id from tags where name = 'online') where tags_id = (select id from tags where name = 'Online Game');
--update articles_tags set tags_id = (select id from tags where name = 'Mehrspieler') where tags_id = (select id from tags where name = 'Multiplayer');

update articles_tags set tags_id = (select id from tags where name = 'Strategie') where tags_id = (select id from tags where name = 'Stategie');
update articles_tags set tags_id = (select id from tags where name = 'Engines') where tags_id = (select id from tags where name = 'engine');

-- Tag anlegen: Feral Store, dann alle Titel mit Feral Interactive und Spiele und Kommerziell zuweisen
insert into tags(id, created, name, taggroup_id) values (nextval('hibernate_sequence'), current_timestamp, 'Feral Store', (select id from taggroups where name = 'STORE'));

insert into articles_tags(article_id, tags_id)
    select a.id as article_id, (select id from tags where name = 'Feral Store') as tags_id from articles a 
    left join articles_tags at1 on at1.article_id = a.id and at1.tags_id = (select id from tags where name = 'Feral Interactive')
    left join articles_tags at2 on at2.article_id = a.id and at2.tags_id = (select id from tags where name = 'Spiele')
    left join articles_tags at3 on at3.article_id = a.id and at3.tags_id = (select id from tags where name = 'Kommerziell')
    where at1.tags_id is not null and at2.tags_id is not null and at3.tags_id is not null;

