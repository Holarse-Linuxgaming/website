create type attachment_data_type as enum ('url', 'storage');

create table if not exists attachment_groups(
    id integer primary key default nextval('hibernate_sequence'),
    code varchar(255) not null unique,
    label varchar(255) not null
);

create table if not exists attachment_types(
    id integer primary key default nextval('hibernate_sequence'),
    code varchar(255) not null,
    label varchar(255) not null,
    attachment_group_id integer references attachment_groups(id),
    datatype attachment_data_type,
    unique(code, attachment_group_id)
);

create table if not exists attachments(
    id integer primary key default nextval('hibernate_sequence'),

    nodeid integer not null,

    weight integer default 0,
    description varchar(255),

    attachment_type_id integer references attachment_types(id),

    attachment_data varchar(4096),

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP
);