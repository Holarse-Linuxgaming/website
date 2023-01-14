create type drückblick_source_type as enum ('grabber', 'bot', 'manual');

create table if not exists drückblick_entries (
	id integer primary key default nextval('hibernate_sequence'),
        reporter varchar(255),
        name varchar(255),
        message varchar(255) not null,
        url varchar(2048) not null,
        changelog varchar(2048),
        category varchar(255),
        source drückblick_source_type not null,

        done bool not null default false,
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP		
);
