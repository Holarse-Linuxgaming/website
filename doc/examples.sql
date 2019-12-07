-- Abfrage:
select pid, ptitle, purl, content, image from search.mv_searchindex
where document @@ to_tsquery('german', 'Echtzeit')
ORDER BY ts_rank(document, to_tsquery('german', 'Echtzeit')) DESC;

-- tagbasierte Suche
select * from search.mv_Searchindex where tags @> array['Spiele'::varchar, 'Horror'::varchar];

-- suchbeispiel für teilwort und titel
select * from search.mv_suggestions where word @@ to_tsquery('simple', 'kr:*') and wtype = 1;

-- suchbeispiel für teilwort und tag
select * from search.mv_suggestions where word @@ to_tsquery('simple', 'kr:*') and wtype = 2;

-- migration
--insert into accesslog(accessed, campaignkeyword, campaignname, httpstatus, ipaddress, nodeid, referer, searchword, url
--                        useragent, visitorid, bot) select pv.created as accessed, campaignkeyword, campaignname, httpstatus, ipaddress,
--                        nodeid, referer, searchword, url, useragent, visitorid, false as bot from pv;


-- admin-userrechte, benutzer müssen vorhanden sein für dieses statement
insert into users_roles(users_id, roles_id) values ((select id from users where login = 'comrad' limit 1), (select id from roles where code = 'ADMIN' limit 1));