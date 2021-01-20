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
        a.id AS pid, -- für referenz
        a.title AS ptitle, -- für ergebnisanzeiuge
        concat_ws(';', a.alternativetitle1, a.alternativetitle2, a.alternativetitle3, a.alternativetitle4, a.alternativetitle5) AS psubtitles, -- für ergebnisanzeige
        concat('/wiki/', a.slug) AS purl, -- für schnelles verlinken
        a.content AS content,
        att.attachmentdata AS image,
        string_agg(tags.name, ';') AS tags,
--        setweight(to_tsvector('simple', unaccent(a.title)), 'A') ||		
--	setweight(to_tsvector('simple', coalesce(unaccent(a.alternativetitle1), '')), 'A') ||			
--	setweight(to_tsvector('simple', coalesce(unaccent(a.alternativetitle2), '')), 'A') ||			
--	setweight(to_tsvector('simple', coalesce(unaccent(a.alternativetitle3), '')), 'A') ||			
--	setweight(to_tsvector('simple', coalesce(unaccent(a.alternativetitle4), '')), 'A') ||			
--	setweight(to_tsvector('simple', coalesce(unaccent(a.alternativetitle5), '')), 'A') ||						  
        setweight(to_tsvector(a.title_lang::regconfig, unaccent(a.title)), 'A') ||
        setweight(to_tsvector(alternativetitle1_lang::regconfig, coalesce(unaccent(a.alternativetitle1), '')), 'A') ||
        setweight(to_tsvector(alternativetitle2_lang::regconfig, coalesce(unaccent(a.alternativetitle2), '')), 'A') ||
        setweight(to_tsvector(alternativetitle3_lang::regconfig, coalesce(unaccent(a.alternativetitle3), '')), 'A') ||
        setweight(to_tsvector(alternativetitle4_lang::regconfig, coalesce(unaccent(a.alternativetitle4), '')), 'A') ||
        setweight(to_tsvector(alternativetitle5_lang::regconfig, coalesce(unaccent(a.alternativetitle5), '')), 'A') ||        setweight(to_tsvector('german', coalesce(a.content, '')), 'B') -- content will be mostly german
--        setweight(to_tsvector(name_lang::regconfig, tags.name), 'C')
	AS document,
        'article' :: nodetype AS doctype
    FROM articles a
    LEFT JOIN articles_tags ON articles_tags.article_id = a.id
    LEFT JOIN tags ON tags.id = articles_tags.tags_id
    LEFT JOIN attachments att ON att.id = (
        SElECT id
        FROM attachments
        WHERE nodeid = a.id AND attachmenttype = 'SCREENSHOT' AND attachmentgroup = 'IMAGE' ORDER BY id LIMIT 1
    )
	WHERE NOT deleted AND published
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
        setweight(to_tsvector(title_lang::regconfig, unaccent(n.title)), 'B') ||
        setweight(to_tsvector(subtitle_lang::regconfig, coalesce(unaccent(n.subtitle), '')), 'D') ||
        setweight(to_tsvector(content_lang::regconfig, coalesce(n.content, '')), 'C')  AS document,
        'news' :: nodetype AS doctype
    FROM news n
    LEFT JOIN attachments att on att.id = (
        SELECT id
        FROM attachments
        WHERE nodeid = n.id AND attachmenttype = 'SCREENSHOT' AND attachmentgroup = 'IMAGE' ORDER BY id limit 1
    )
	WHERE NOT deleted AND published
    GROUP BY n.id, att.attachmentdata
);
ALTER MATERIALIZED VIEW search.mv_searchindex OWNER TO :dbUser;
CREATE INDEX IF NOT EXISTS idx_fts_search ON search.mv_searchindex USING gin(document);
CREATE INDEX IF NOT EXISTS idx_fts_tags ON search.mv_searchindex (tags, doctype);


--- Suggestions
CREATE MATERIALIZED VIEW IF NOT EXISTS search.mv_suggestions AS (
    -- Suggestions from titles
    SELECT
        to_tsvector(title_lang::regconfig, a.title) ||
        to_tsvector(alternativetitle1_lang::regconfig, coalesce(a.alternativetitle1, '')) ||
        to_tsvector(alternativetitle2_lang::regconfig, coalesce(a.alternativetitle2, '')) ||
        to_tsvector(alternativetitle3_lang::regconfig, coalesce(a.alternativetitle3, '')) || 
        to_tsvector(alternativetitle4_lang::regconfig, coalesce(a.alternativetitle4, '')) || 
        to_tsvector(alternativetitle5_lang::regconfig, coalesce(a.alternativetitle5, '')) AS word,
        a.title AS wlabel,
        'title'::suggestiontype AS wtype,
        0 AS use_count
    FROM articles a

    UNION

    -- Suggestions from tags
    SELECT
        to_tsvector('german', coalesce(t.name, '')) AS word,
        t.name AS wlabel,
        'tag'::suggestiontype AS wtype,
        (SELECT count(1) FROM articles_tags at WHERE at.tags_id = t.id) AS use_count
    FROM tags t
);
ALTER MATERIALIZED VIEW search.mv_suggestions OWNER TO :dbUser;
CREATE INDEX IF NOT EXISTS idx_suggestions ON search.mv_suggestions USING gin(word, wtype);

-- Slug-View
CREATE MATERIALIZED VIEW IF NOT EXISTS search.mv_slugs AS (
    select id, slug, nodetype from
    (
        (select id, slug, 'news' as nodetype from news where branch = 'master')
        union
        (select id, slug, 'article' as nodetype from articles where branch = 'master')
        union
        (select nodeid as id, slug, nodetype as nodetype from slugs)
    ) as foo
);
ALTER MATERIALIZED VIEW search.mv_slugs OWNER TO :dbUser;
CREATE INDEX IF NOT EXISTS idx_slugview ON search.mv_slugs (slug, nodetype);

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

-- End
\echo '----'
\echo 'Done!'
