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
    'wiki/' || articles.slug AS purl,
    'news'::text AS pnodetype,    
    concat_ws(', '::text, articles.alternativetitle1, articles.alternativetitle2, articles.alternativetitle3) AS palternativetitle,
    articles.content AS pcontent,
    (((setweight(to_tsvector('holarse_de'::regconfig, articles.title::text), 'A'::"char") || setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle1::text), 'B'::"char")) || setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle2::text), 'B'::"char")) || setweight(to_tsvector('holarse_de'::regconfig, articles.alternativetitle3::text), 'B'::"char")) || setweight(to_tsvector('holarse_de'::regconfig, articles.content::text), 'B'::"char") AS document
   FROM articles
UNION
 SELECT comments.id AS pid,
    coalesce(a.title, n.title) || ' (Kommentar #' || comments.id || ')' as ptitle,
    case when a.id is not null then 'wiki/' || a.slug || '#comment-' || comments.id
	 when n.id is not null then 'news/' || n.slug || '#comment-' || comments.id
	 else null
    end as purl,
    'comments'::text AS pnodetype,    
    NULL::character varying AS palternativetitle,
    comments.content AS pcontent,
    setweight(to_tsvector('holarse_de'::regconfig, comments.content::text), 'A'::"char") AS document
   FROM comments
   left join articles a on a.id = comments.node_id
   left join news n on n.id = comments.node_id
UNION
 SELECT news.id AS pid,
    news.title AS ptitle,
    'news/' || news.slug AS purl,    
    'news'::text AS pnodetype,    
    news.subtitle AS palternativetitle,
    news.content AS pcontent,
    (setweight(to_tsvector('holarse_de'::regconfig, news.title::text), 'A'::"char") || setweight(to_tsvector('holarse_de'::regconfig, news.subtitle::text), 'B'::"char")) || setweight(to_tsvector('holarse_de'::regconfig, news.content::text), 'B'::"char") AS document
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
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'GENRE');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'MULTIPLAYER');
insert into taggroups(id, name) values (nextval('hibernate_sequence'), 'STORE');

