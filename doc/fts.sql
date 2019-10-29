create schema search;

-- Suchindex anlegen:
--drop materialized view mv_searchindex;
create materialized view search.mv_searchindex as (
    -- articles
    select  
        a.id as pid,
        a.title as ptitle,
        a.slug as purl,
        a.content as content,
        att.attachmentdata as image,
        string_agg(tags.name, ';') as tags,
        setweight(to_tsvector('english', unaccent(a.title)), 'A') || 
        setweight(to_tsvector('english', coalesce(unaccent(a.alternativetitle1), '')), 'C') || 
        setweight(to_tsvector('english', coalesce(unaccent(a.alternativetitle2), '')), 'C') || 
        setweight(to_tsvector('english', coalesce(unaccent(a.alternativetitle3), '')), 'C') ||
        setweight(to_tsvector('german', coalesce(a.content, '')), 'B') ||
        setweight(to_tsvector('simple', string_agg(tags.name, ' ')), 'C') as document
    from articles a
    left join articles_tags on articles_tags.article_id = a.id
    left join tags on tags.id = articles_tags.tags_id
    left join attachments att on att.id = (
        select id from attachments where nodeid = a.id and attachmenttype = 'SCREENSHOT' and attachmentgroup = 'IMAGE' order by id limit 1
    )
	where not draft and not deleted and published
    group by a.id, att.attachmentdata

	union

    -- news
    select
        n.id as pid,
        n.title as ptitle,
        n.slug as purl,
        n.content as content,
        att.attachmentdata as image,
        '' as tags,
        setweight(to_tsvector('german', unaccent(n.title)), 'A') ||
        setweight(to_tsvector('german', coalesce(unaccent(n.subtitle), '')), 'C') ||
        setweight(to_tsvector('german', coalesce(n.content, '')), 'B')  as document
    from news n
    left join attachments att on att.id = (
        select id from attachments where nodeid = n.id and attachmenttype = 'SCREENSHOT' and attachmentgroup = 'IMAGE' order by id limit 1
    )
	where not deleted and not draft and published
    group by n.id, att.attachmentdata
);

create index idx_fts_search on search.mv_searchindex using gin(document);
create index on search.mv_searchindex using gin(tags);

-- Abfrage:
select pid, ptitle, purl, content, image from search.mv_searchindex
where document @@ to_tsquery('german', 'Echtzeit')
ORDER BY ts_rank(document, to_tsquery('german', 'Echtzeit')) DESC;

-- tagbasierte Suche
select * from search.mv_Searchindex where tags @> array['Spiele'::varchar, 'Horror'::varchar];

-- suchwort-vorschläge
create materialized view search.mv_suggestions as 
select 
    to_tsvector('english', a.title) ||
    to_tsvector('english', coalesce(a.alternativetitle1, '')) ||
    to_tsvector('english', coalesce(a.alternativetitle2, '')) ||
    to_tsvector('english', coalesce(a.alternativetitle3, '')) as word,
    a.title as wlabel,
    1 as wtype
from articles a

union

select
    to_tsvector('simple', coalesce(t.name, '')) as word,
    t.name as wlabel,
    2 as wtype
from tags t;

-- suchindex für vorschläge (tags- und titel-autovervollständigung)
create extension btree_gin;
create index idx_suggestions on search.mv_suggestions using gin(word, wtype);

-- suchbeispiel für teilwort und tag
select * from search.mv_suggestions
where word @@ to_tsquery('simple', 'kr:*') and wtype = 2;