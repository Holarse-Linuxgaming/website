create table if not exists comments (
	id integer primary key default nextval('hibernate_sequence'),
	nodeid integer not null,
	
	userid integer references users(id),
	
	content varchar(16384),
	content_lang varchar(12) default 'german',
	
    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP	
);