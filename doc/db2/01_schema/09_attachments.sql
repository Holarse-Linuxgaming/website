create table if not exists attachments(
    id integer primary key default nextval('hibernate_sequence'),

    nodeid integer not null,

    ordering integer default 0,
    description varchar(255),

    attachment_group varchar(255),
    attachment_type varchar(255),
    attachment_datatype varchar(255),

    attachment_data varchar(4096),

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id)	
);