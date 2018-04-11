-- login erzeugen
CREATE ROLE holarse NOSUPERUSER NOCREATEDB NOCREATEROLE INHERIT LOGIN NOREPLICATION;

-- fts 
create extension unaccent;
create extension pg_trgm;

create text search configuration holarse_de ( COPY = german );
ALTER TEXT SEARCH CONFIGURATION holarse_de ALTER MAPPING
FOR hword, hword_part, word WITH unaccent, german_stem;

-- Materialized View: public.search_index
DROP MATERIALIZED VIEW public.search_index;

CREATE MATERIALIZED VIEW public.search_index AS 
 SELECT articles.id AS pid,
    articles.title AS ptitle,
    concat_ws(', ', articles.alternativetitle1, articles.alternativetitle2, articles.alternativetitle3) AS palternativetitle,
    'wiki'::text AS pnodetype,
    articles.content AS pcontent,
    (	setweight(to_tsvector('holarse_de'::regconfig, articles.title::text), 'A'::"char") || 
	setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle1::text), 'B'::"char") || 
	setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle2::text), 'B'::"char") || 
	setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle3::text), 'B'::"char") || 
	setweight(to_tsvector('holarse_de'::regconfig, articles.content::text), 'B'::"char")
    ) AS document
   FROM articles
UNION
 SELECT comments.id AS pid,
    NULL::character varying AS ptitle,
    NULL::character varying AS palternativetitle,
    'comments'::text AS pnodetype,
    comments.content AS pcontent,
    setweight(to_tsvector('holarse_de'::regconfig, comments.content::text), 'A'::"char") AS document
   FROM comments
UNION
 SELECT news.id AS pid,
    news.title AS ptitle,
    news.subtitle AS palternativetitle,
    'news'::text AS pnodetype,
    news.content AS pcontent,
    (	setweight(to_tsvector('holarse_de'::regconfig, news.title::text), 'A'::"char") || 
	setweight(to_tsvector('holarse_de'::regconfig, news.subtitle::text), 'B'::"char") || 
	setweight(to_tsvector('holarse_de'::regconfig, news.content::text), 'B'::"char")
    ) AS document
   FROM news
WITH DATA;

ALTER TABLE public.search_index
  OWNER TO holarse;

-- rollen anlegen
insert into roles (id, code) values (nextval('hibernate_sequence'), 'ADMIN');
insert into roles (id, code) values (nextval('hibernate_sequence'), 'TRUSTED_USER');
insert into roles (id, code) values (nextval('hibernate_sequence'), 'MODERATOR');
insert into roles (id, code) values (nextval('hibernate_sequence'), 'REPORTER');

insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'LICENSE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'GENRES');
