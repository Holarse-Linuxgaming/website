-- login erzeugen
CREATE ROLE holarse NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN NOREPLICATION;

--create sequence revision_seq;

-- fts 
-- create extension unaccent;
-- create extension pg_trgm;
-- 
-- create text search configuration holarse_de ( COPY = german );
-- ALTER TEXT SEARCH CONFIGURATION holarse_de ALTER MAPPING
-- FOR hword, hword_part, word WITH unaccent, german_stem;
-- 
-- -- Materialized View: public.search_index
-- 
-- DROP MATERIALIZED VIEW public.search_index;
-- 
-- CREATE MATERIALIZED VIEW public.search_index AS 
-- SELECT articles.id AS pid,
--     articles.title AS ptitle,
--     'wiki/' || articles.slug AS purl,
--     'news'::text AS pnodetype,    
--     concat_ws(', '::text, articles.alternativetitle1, articles.alternativetitle2, articles.alternativetitle3) AS palternativetitle,
--     string_agg(tags.name, ', ') as ptags,
--     articles.content AS pcontent,
--     (((	setweight(to_tsvector('holarse_de'::regconfig, articles.title::text), 'A'::"char") || 
-- 	setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle1::text), 'B'::"char")) || 
-- 	setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle2::text), 'B'::"char")) || 
-- 	setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle3::text), 'B'::"char")) || 
-- 	setweight(to_tsvector('holarse_de'::regconfig, articles.content::text), 'B'::"char") || 
-- 	setweight(to_tsvector('holarse_de'::regconfig, string_agg(tags.name, ' ')), 'B'::"char") AS document
--    FROM articles
--    left join articles_tags on articles_tags.article_id = articles.id
--    left join tags on articles_tags.tags_id = tags.id
--    group by articles.id
-- UNION
--  SELECT comments.id AS pid,
--     coalesce(a.title, n.title) || ' (Kommentar #' || comments.id || ')' as ptitle,
--     case when a.id is not null then 'wiki/' || a.slug || '#comment-' || comments.id
-- 	 when n.id is not null then 'news/' || n.slug || '#comment-' || comments.id
-- 	 else null
--     end as purl,
--     'comments'::text AS pnodetype,    
--     NULL::character varying AS palternativetitle,
--     NULL::character varying AS ptags,
--     comments.content AS pcontent,
--     setweight(to_tsvector('holarse_de'::regconfig, comments.content::text), 'A'::"char") AS document
--    FROM comments
--    left join articles a on a.id = comments.node_id
--    left join news n on n.id = comments.node_id
-- UNION
--  SELECT news.id AS pid,
--     news.title AS ptitle,
--     'news/' || news.slug AS purl,    
--     'news'::text AS pnodetype,    
--     news.subtitle AS palternativetitle,
--     NULL::character varying AS ptags,    
--     news.content AS pcontent,
--     (setweight(to_tsvector('holarse_de'::regconfig, news.title::text), 'A'::"char") || setweight(to_tsvector('holarse_de'::regconfig, news.subtitle::text), 'B'::"char")) || setweight(to_tsvector('holarse_de'::regconfig, news.content::text), 'B'::"char") AS document
--    FROM news
-- 
-- WITH DATA;
-- 
-- ALTER TABLE public.search_index
--   OWNER TO holarse;


-- rollen anlegen
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'ADMIN', 0);
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'MODERATOR', 100);
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'REPORTER', 250);
insert into roles (id, code, clearancelevel) values (nextval('hibernate_sequence'), 'TRUSTED_USER', 500);

insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'LICENSE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'GENRE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'MULTIPLAYER');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'STORE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'FRANCHISE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'COMPANY');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'DEVELOPER');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'PUBLISHER');

create table "public".attachments
(
	id BIGSERIAL not null primary key,
	created TIMESTAMP default now(),
	updated TIMESTAMP default now(),
	attachmentdatatype INT,
	description VARCHAR(2048),
	mimetype VARCHAR(255),
	ordering BIGINT,
	uri VARCHAR(4096),
	uritype VARCHAR(255),
        nodeid bigint
);

create extension pgcrypto;

insert into apiusers (id, login, rolename, token) values (nextval('hibernate_sequence'), 'dummy', 'API_INTERNAL', 'ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940'); -- geheim
