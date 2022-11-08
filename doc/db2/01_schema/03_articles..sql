-- Die Artikel-Historie
create table if not exists article_revisions(
    id integer primary key not null default nextval('hibernate_sequence'), 
    nodeid integer not null,

    revision integer not null default nextval('revision_sequence'), 

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id),
    changelog varchar(255),
    
    title1 varchar(512),
    title1_lang varchar(12) default 'english',
    title2 varchar(512),
    title2_lang varchar(12) default 'english',
    title3 varchar(512),
    title3_lang varchar(12) default 'english',
    title4 varchar(512),
    title4_lang varchar(12) default 'english',
    title5 varchar(512),
    title5_lang varchar(12) default 'english',
    title6 varchar(512),
    title6_lang varchar(12) default 'english',
    title7 varchar(512),
    title7_lang varchar(12) default 'english',
    
    teaser varchar(512),
    teaser_lang varchar(12) default 'german',
    
    content varchar(16384),
    content_lang varchar(12) default 'german'
);

-- Artikel
create table if not exists articles(
	id integer primary key not null default nextval('hibernate_sequence'), 
	nodeid integer not null default nextval('node_sequence'),
	revisionid integer references article_revisions(id),
        drupalid integer
);

