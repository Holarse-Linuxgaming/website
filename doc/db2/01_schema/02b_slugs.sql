-- Die Slugs aller Nodes
create table if not exists node_slugs(
    id integer primary key default nextval('hibernate_sequence'),    

    nodeid integer not null,

    name varchar(255) not null,
    main bool not null default false,

    slug_context node_type not null,

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,

    unique (name, slug_context)
);

-- Die Slugs aller User
create table if not exists user_slugs(
    id integer primary key default nextval('hibernate_sequence'),    

    user_id integer references users(id),

    name varchar(255) not null unique,

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP
);
