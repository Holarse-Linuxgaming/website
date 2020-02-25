--------
-- This script can be run by an admin user (e.g. postgres)
-- or the holarse user.
-- It is important, that HolaCMS has been started after 01_as_postgres.sql!
--------

-- Variables for the script
\set dbUser holarse


-- 1. Table generation
--- Search index
\echo '-- Create tables --'
CREATE MATERIALIZED VIEW IF NOT EXISTS search.mv_searchindex AS (
    -- articles
    SELECT
        a.id AS pid,
        a.title AS ptitle,
        concat_ws(';', a.alternativetitle1, a.alternativetitle2, a.alternativetitle3) AS psubtitles,
        concat('/wiki/', a.slug) AS purl,
        a.content AS content,
        att.attachmentdata AS image,
        string_agg(tags.name, ';') AS tags,
        setweight(to_tsvector('english', search.unaccent(a.title)), 'A') || 
        setweight(to_tsvector('english', coalesce(search.unaccent(a.alternativetitle1), '')), 'C') || 
        setweight(to_tsvector('english', coalesce(search.unaccent(a.alternativetitle2), '')), 'C') || 
        setweight(to_tsvector('english', coalesce(search.unaccent(a.alternativetitle3), '')), 'C') ||
        setweight(to_tsvector('german', coalesce(a.content, '')), 'B') ||
        setweight(to_tsvector('simple', string_agg(tags.name, ' ')), 'C') AS document,
        'article' :: nodetype AS doctype
    FROM articles a
    LEFT JOIN articles_tags ON articles_tags.article_id = a.id
    LEFT JOIN tags ON tags.id = articles_tags.tags_id
    LEFT JOIN attachments att ON att.id = (
        SElECT id
        FROM attachments
        WHERE nodeid = a.id AND attachmenttype = 'SCREENSHOT' AND attachmentgroup = 'IMAGE' ORDER BY id LIMIT 1
    )
	WHERE NOT draft AND NOT deleted AND published
    GROUP BY a.id, att.attachmentdata

	UNION

    -- news
    SELECT
        n.id AS pid,
        n.title AS ptitle,
        n.subtitle AS psubtitles,
        concat('/news/', n.slug) AS purl,
        n.content AS content,
        att.attachmentdata AS image,
        '' AS tags,
        setweight(to_tsvector('german', search.unaccent(n.title)), 'A') ||
        setweight(to_tsvector('german', coalesce(search.unaccent(n.subtitle), '')), 'C') ||
        setweight(to_tsvector('german', coalesce(n.content, '')), 'B')  AS document,
        'news' :: nodetype AS doctype
    FROM news n
    LEFT JOIN attachments att on att.id = (
        SELECT id
        FROM attachments
        WHERE nodeid = n.id AND attachmenttype = 'SCREENSHOT' AND attachmentgroup = 'IMAGE' ORDER BY id limit 1
    )
	WHERE NOT deleted AND NOT draft AND published
    GROUP BY n.id, att.attachmentdata
);
ALTER MATERIALIZED VIEW search.mv_searchindex OWNER TO :dbUser;
CREATE INDEX IF NOT EXISTS idx_fts_search ON search.mv_searchindex USING gin(document);
CREATE INDEX IF NOT EXISTS idx_fts_tags ON search.mv_searchindex (tags, doctype);


--- Suggestions
CREATE MATERIALIZED VIEW IF NOT EXISTS search.mv_suggestions AS (
    -- Suggestions from titles
    SELECT
        to_tsvector('english', a.title) ||
        to_tsvector('english', coalesce(a.alternativetitle1, '')) ||
        to_tsvector('english', coalesce(a.alternativetitle2, '')) ||
        to_tsvector('english', coalesce(a.alternativetitle3, '')) AS word,
        a.title AS wlabel,
        'title'::suggestiontype AS wtype,
        0 AS use_count
    FROM articles a

    UNION

    -- Suggestions from tags
    SELECT
        to_tsvector('simple', coalesce(t.name, '')) AS word,
        t.name AS wlabel,
        'tag'::suggestiontype AS wtype,
        (SELECT count(1) FROM articles_tags at WHERE at.tags_id = t.id) AS use_count
    FROM tags t
);
ALTER MATERIALIZED VIEW search.mv_suggestions OWNER TO :dbUser;
CREATE INDEX IF NOT EXISTS idx_suggestions ON search.mv_suggestions USING gin(word, wtype);


-- 2. Triggers and functions
\echo '-- Create triggers and functions --'
CREATE OR REPLACE FUNCTION update_tag_use_count() RETURNS TRIGGER AS $trg_tag_usecount$
   BEGIN
      UPDATE tags SET use_count = (SELECT count(1) FROM articles_tags WHERE tags_id = new.tags_id) WHERE id = new.tags_id;
      RETURN NEW;
   END;
$trg_tag_usecount$ LANGUAGE plpgsql;
ALTER FUNCTION update_tag_use_count() OWNER TO :dbUser;

DROP TRIGGER IF EXISTS trg_tag_usecount ON articles_tags CASCADE;
CREATE TRIGGER trg_tag_usecount AFTER INSERT OR DELETE ON articles_tags
FOR EACH ROW EXECUTE PROCEDURE update_tag_use_count();

-- 3. Table Generation
\echo '-- Generating basic tables --'
--- Logging
CREATE TABLE IF NOT EXISTS logging.accesslog (
    id bigserial,
    nodeid bigint,
    visitorid varchar(255),
    campaignkeyword varchar(255),
    campaignname varchar(255),
    httpstatus int,
    ipaddress varchar(255),
    referer varchar(2083),
    searchword varchar(255),
    url varchar(2083),
    useragent varchar(255),
    bot boolean DEFAULT false,
    accessed timestamp with time zone DEFAULT CURRENT_TIMESTAMP NOT NULL
) PARTITION BY range(accessed);
ALTER TABLE logging.accesslog OWNER TO :dbUser;

---- Index for accesslog
CREATE INDEX IF NOT EXISTS accesslog_accessed_idx ON logging.accesslog (accessed);
CREATE INDEX IF NOT EXISTS accesslog_nodeid_accessed_idx ON logging.accesslog (nodeid, accessed);

---- Table data for logging (FIXME!)
CREATE TABLE logging.accesslog_2019 partition OF logging.accesslog FOR VALUES FROM ('2019-01-01') TO ('2020-01-01');
CREATE TABLE logging.accesslog_2020 partition OF logging.accesslog FOR VALUES FROM ('2020-01-01') TO ('2021-01-01');
CREATE TABLE logging.accesslog_2021 partition OF logging.accesslog FOR VALUES FROM ('2021-01-01') TO ('2022-01-01');
CREATE TABLE logging.accesslog_2022 partition OF logging.accesslog FOR VALUES FROM ('2022-01-01') TO ('2023-01-01');
CREATE TABLE logging.accesslog_2023 partition OF logging.accesslog FOR VALUES FROM ('2023-01-01') TO ('2024-01-01');
CREATE TABLE logging.accesslog_2024 partition OF logging.accesslog FOR VALUES FROM ('2024-01-01') TO ('2025-01-01');
CREATE TABLE logging.accesslog_2025 partition OF logging.accesslog FOR VALUES FROM ('2025-01-01') TO ('2026-01-01');



-- End
\echo '----'
\echo 'Done!'
