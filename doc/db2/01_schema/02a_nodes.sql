-- Eindeutiger Zähler für "Nodes" 
create sequence node_sequence start with 10000;
grant select, update on sequence node_sequence to holarse;

create type node_type as enum ('article', 'news', 'thread', 'drückblick');

-- Gemeinsame Node-Tabellen

-- Enthält die Statusdaten eines Artikels, News, usw.
create table if not exists node_status (
    id integer primary key default nextval('hibernate_sequence'),
    nodeid integer not null unique,
    archived bool default false,
    commentable bool default true,
    published bool default false,
    deleted bool default false,

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,
    user_id integer references users(id)
);

-- Standard-Zeilen-Locktabelle
create table if not exists entity_writelocks(
    id integer primary key default nextval('hibernate_sequence'),
    entity node_type not null,
    row_id integer not null unique,
    user_id integer references users(id),
    write_lock_updated timestamptz
);


-- Eindeutiger Zähler für "Revisionen" 
create sequence revision_sequence start with 10000;
grant select, update on sequence revision_sequence to holarse;