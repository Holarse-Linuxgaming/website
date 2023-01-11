create type drueckblick_source_type as enum ('grabber', 'bot', 'manual');

create table if not exists drueckblick_entries (
	id integer primary key default nextval('hibernate_sequence'),
        reporter varchar(255),
        name varchar(255),
        description varchar(255) not null,
        url varchar(255) not null,
        category varchar(255),
        source drueckblick_source_type not null,

        done bool not null default false,
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP		
);
