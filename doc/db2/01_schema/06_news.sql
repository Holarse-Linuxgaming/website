create table news_revisions(
	id integer primary key default nextval('hibernate_sequence'),

    nodeid integer not null,

    revision integer not null default nextval('revision_sequence'), 

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id),
    changelog varchar(255),
    
    title varchar(255),
    title_lang varchar(12) not null default 'german',
    
    content varchar(16384),
    content_lang varchar(12) not null default 'german', 
    
    category varchar(255),
    category_lang varchar(12) not null default 'german'
);

create table news(
	id integer primary key not null default nextval('hibernate_sequence'), 
	nodeid integer not null default nextval('node_sequence'),
	revisionid integer references news_revisions(id),
    drupalid integer
);

