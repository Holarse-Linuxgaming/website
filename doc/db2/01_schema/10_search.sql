CREATE MATERIALIZED VIEW IF NOT EXISTS mv_searchindex AS (

SELECT
        ar.nodeid AS nodeid, -- für referenz
        ar.title1 AS ptitle, -- für ergebnisanzeiuge
        concat_ws(';', ar.title2, ar.title3, ar.title4, ar.title5, ar.title6, ar.title7) AS psubtitles, -- für ergebnisanzeige
        '/wiki/' || nsl.name as purl, -- für schnelles verlinken
        ar.content AS content,
        att.attachment_data AS image,
        string_agg(tags.slug, '@@@') AS tags,				  
        setweight(to_tsvector(ar.title1_lang::regconfig,          unaccent(ar.title1)), 'A') ||
        setweight(to_tsvector(ar.title2_lang::regconfig, coalesce(unaccent(ar.title2), '')), 'A') ||
        setweight(to_tsvector(ar.title3_lang::regconfig, coalesce(unaccent(ar.title3), '')), 'A') ||
        setweight(to_tsvector(ar.title4_lang::regconfig, coalesce(unaccent(ar.title4), '')), 'A') ||
        setweight(to_tsvector(ar.title5_lang::regconfig, coalesce(unaccent(ar.title5), '')), 'A') ||
        setweight(to_tsvector(ar.title6_lang::regconfig, coalesce(unaccent(ar.title6), '')), 'A') ||        
        setweight(to_tsvector(ar.title7_lang::regconfig, coalesce(unaccent(ar.title7), '')), 'A') ||        
        setweight(to_tsvector('german', coalesce(ar.content, '')), 'B') || -- content will be mostly german
        setweight(to_tsvector('simple', string_agg(tags.name, '@@@')), 'C') -- tags should be matched exactly
	AS document,
        'article' :: node_type AS doctype,
        ar.created as last_update
    FROM article_revisions ar
    INNER JOIN articles a on a.revisionid = ar.id  
    LEFT JOIN node_tags ON node_tags.nodeid = ar.nodeid
    LEFT JOIN tags ON tags.id = node_tags.tagid
    LEFT JOIN attachments att ON att.id = (
		select a2.id from attachments a2
		inner join attachment_types at2 on at2.id = a2.attachment_type_id 
		where at2.code = 'screenshot' order by a2.weight, a2.id limit 1
    )
    left join node_slugs nsl on nsl.id = (
    	select id
    	from node_slugs
    	where node_slugs.nodeid = ar.nodeid order by updated limit 1
    )
    inner join node_status ns on ns.nodeid = ar.nodeid 
	WHERE NOT ns.deleted AND ns.published
    GROUP BY ar.id, nsl.name, att.attachment_data
     
union -- ab hier news

SELECT
        nr.nodeid AS nodeid, -- für referenz
        nr.title AS ptitle, -- für ergebnisanzeiuge
        '' as psubtitle, -- für spaltenkompatibilität zu articles
        nsl.name as purl, -- für schnelles verlinken
        nr.content AS content,
        att.attachment_data AS image,
        string_agg(tags.name, '@@@') AS tags,				  
        setweight(to_tsvector(nr.title_lang::regconfig, unaccent(nr.title)), 'A') ||  
        setweight(to_tsvector('german', coalesce(nr.content, '')), 'B') || -- content will be mostly german
        setweight(to_tsvector('simple', string_agg(tags.name, '@@@')), 'C') -- tags should be matched exactly
	AS document,
        'news' :: node_type AS doctype,
        nr.created as last_update
    FROM news_revisions nr
    INNER JOIN news n on n.revisionid = nr.id  
    LEFT JOIN node_tags ON node_tags.nodeid = nr.nodeid
    LEFT JOIN tags ON tags.id = node_tags.tagid
    LEFT JOIN attachments att ON att.id = (
		select a2.id from attachments a2
		inner join attachment_types at2 on at2.id = a2.attachment_type_id 
		where at2.code = 'screenshot' order by a2.weight, a2.id limit 1
    )
    left join node_slugs nsl on nsl.id = (
    	select id
    	from node_slugs
    	where node_slugs.nodeid = nr.nodeid order by updated limit 1
    )
    inner join node_status ns on ns.nodeid = nr.nodeid 
	WHERE NOT ns.deleted AND published
    GROUP BY nr.id, nsl.name, att.attachment_data

union -- ab hier forumthreads   

    select 
    ft.nodeid as nodeid, -- für referenz
    ft.title as ptitle, --für ergebnisanzeige
    '' as psubtitle, -- für spaltenkompatibilität zu articles
    nsl.name as purl, -- für schnelles verlinken
    ft.content as content,
    '' as image,
    '' as tags,
    setweight(to_tsvector(ft.title_lang::regconfig, unaccent(ft.title)), 'A') ||  
    setweight(to_tsvector('german', coalesce(ft.content, '')), 'B') -- content will be mostly german
    as document,
    'thread' :: node_type as doctype,
    ft.created as last_update
    from forum_threads ft
    inner join forums on forums.id = ft.forumid 
    inner join node_status ns on ns.nodeid = ft.nodeid 
    left join node_slugs nsl on nsl.id = (
            select id
            from node_slugs
            where node_slugs.nodeid = ft.nodeid order by updated limit 1
    )
    WHERE NOT ns.deleted AND published

);

ALTER MATERIALIZED VIEW mv_searchindex OWNER TO holarse;
CREATE INDEX IF NOT EXISTS idx_fts_search ON mv_searchindex USING gin(document);
CREATE INDEX IF NOT EXISTS idx_fts_tags ON mv_searchindex (tags, doctype);


---
--- Suggestions
---

---- Suggestiontype
CREATE TYPE suggestion_type AS ENUM ('title', 'tag');


CREATE MATERIALIZED VIEW IF NOT EXISTS mv_suggestions AS (
    -- Suggestions from titles
    SELECT
        to_tsvector(ar.title1_lang::regconfig, ar.title1) || 
        to_tsvector(ar.title2_lang::regconfig, coalesce(ar.title2, '')) || 
        to_tsvector(ar.title3_lang::regconfig, coalesce(ar.title3, '')) || 
        to_tsvector(ar.title4_lang::regconfig, coalesce(ar.title4, '')) || 
        to_tsvector(ar.title5_lang::regconfig, coalesce(ar.title5, '')) || 
        to_tsvector(ar.title6_lang::regconfig, coalesce(ar.title6, '')) || 
        to_tsvector(ar.title7_lang::regconfig, coalesce(ar.title7, '')) AS word,
        ar.title1 AS wlabel,
        'title'::suggestion_type AS wtype,
        0 AS use_count
    FROM article_revisions ar
    INNER JOIN articles a ON a.nodeid = ar.nodeid

    UNION

    -- Suggestions from tags
    SELECT
        to_tsvector('german', coalesce(t.name, '')) AS word,
        t.name AS wlabel,
        'tag'::suggestion_type AS wtype,
        (SELECT count(1) FROM node_tags nt WHERE nt.tagid = t.id) AS use_count
    FROM tags t
);
ALTER MATERIALIZED VIEW mv_suggestions OWNER TO holarse;
CREATE INDEX IF NOT EXISTS idx_suggestions ON mv_suggestions USING gin(word, wtype);
