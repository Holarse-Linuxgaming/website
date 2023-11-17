create table if not exists forums (
	id int primary key default nextval('hibernate_sequence'),
	title varchar(255) not null unique,
	description varchar(1024),
	weight int default 0,
        slug varchar(255) not null
);

create table if not exists forum_threads (
	id integer primary key default nextval('hibernate_sequence'),
	forumid integer references forums(id),
	nodeid integer not null default nextval('node_sequence'),
	title varchar(255) not null,
        title_lang varchar(12) not null default 'german',
        content varchar(16384),
        content_lang varchar(12) not null default 'german',
        created timestamptz not null default CURRENT_TIMESTAMP,
        updated timestamptz not null default CURRENT_TIMESTAMP
);

