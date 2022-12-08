create table if not exists jobs (
	id integer primary key default nextval('hibernate_sequence'),
	queue integer not null default 0,
        context varchar(255) not null,
	data bytea not null,
	
        tries integer not null default 0,
	completed bool not null default false,
        ignore bool not null default false,
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP		
);
