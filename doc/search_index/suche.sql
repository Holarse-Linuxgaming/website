Postgres FTS

create extension unaccent;

create text search configuration holarse_de ( COPY = german );
ALTER TEXT SEARCH CONFIGURATION holarse_de ALTER MAPPING
FOR hword, hword_part, word WITH unaccent, german_stem;

DROP MATERIALIZED VIEW public.search_index;

CREATE MATERIALIZED VIEW public.search_index AS 
 SELECT articles.id AS pid,
    articles.title AS ptitle,
    'article'::text AS pnodetype,
    articles.content AS pcontent,
    setweight(to_tsvector('holarse_de'::regconfig, articles.title::text), 'A'::"char") || setweight(to_tsvector('holarse_de'::regconfig, articles.content::text), 'B'::"char") AS document
   FROM articles
UNION
 SELECT comments.id AS pid,
    ''::character varying AS ptitle,
    'comment'::text AS pnodetype,
    comments.content AS pcontent,
    setweight(to_tsvector('holarse_de'::regconfig, comments.content::text), 'A'::"char") AS document
   FROM comments
UNION
 SELECT news.id AS pid,
    news.title AS ptitle,
    'news'::text AS pnodetype,
    news.content AS pcontent,
    setweight(to_tsvector('holarse_de'::regconfig, news.title::text), 'A'::"char") || setweight(to_tsvector('holarse_de'::regconfig, news.content::text), 'B'::"char") AS document
   FROM news
WITH DATA;

ALTER TABLE public.search_index
  OWNER TO holarse;

select * from search_index where search_index.document @@ to_tsquery('holarse_de', 'Lorem') ORDER BY ts_rank(search_index.document, to_tsquery('holarse_de', 'Endangered & Species')) DESC;;

