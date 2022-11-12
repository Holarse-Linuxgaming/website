-- Die Slugs aller Nodes
create table if not exists node_slugs(
    id integer primary key default nextval('hibernate_sequence'),    

    nodeid integer not null unique,

    name varchar(255) not null,

    slug_context node_type not null,

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id),

    unique (name, slug_context)
);
