create table if not exists news_categories(
    id integer primary key default nextval('hibernate_sequence'),
    name varchar(255) not null,
    active bool default false,
    weight integer default 0,

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id)
);

create table if not exists news_revisions(
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

    news_category_id integer references news_categories(id)
);

create table if not exists news(
    id integer primary key not null default nextval('hibernate_sequence'), 
    nodeid integer not null default nextval('node_sequence'),
    revisionid integer references news_revisions(id),
    drupalid integer
);


create table if not exists news_updates (
    id integer primary key not null default nextval('hibernate_sequence'), 
    nodeid integer not null default nextval('node_sequence'),

    title varchar(255),
    title_lang varchar(12) not null default 'german',

    content varchar(16384),
    content_lang varchar(12) not null default 'german',

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id)
);

