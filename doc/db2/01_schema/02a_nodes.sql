-- Eindeutiger Zähler für "Nodes" 
create sequence node_sequence start with 10000;
grant select, update on sequence node_sequence to holarse;

create type node_type as enum ('article', 'news', 'thread');

-- Gemeinsame Node-Tabellen

-- Enthält die Statusdaten eines Artikels, News, usw.
create table if not exists node_status (
    id integer primary key default nextval('hibernate_sequence'),
    nodeid integer not null unique,
    archived bool default false,
    commentable bool default true,
    published bool default false,
    deleted bool default false,

    updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id)
);

create table if not exists node_writelocks(
    id integer primary key default nextval('hibernate_sequence'),
    nodeid integer not null unique,
    write_lock bool default false,
    write_lock_userid integer references users(id),
    write_lock_updated timestamptz
);


-- Eindeutiger Zähler für "Revisionen" 
create sequence revision_sequence start with 10000;
grant select, update on sequence revision_sequence to holarse;