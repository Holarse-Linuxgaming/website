create table if not exists roles(
	id integer primary key default nextval('hibernate_sequence'),
	name varchar(255) not null unique,
	
	level integer check (level > 0),
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP
);

create table if not exists users (
	id integer primary key default nextval('hibernate_sequence'),
	
	login varchar(255) not null unique,
	email varchar(255) not null unique,
	slug varchar(255) not null unique,
	
	drupalid integer,
	
	hashtype password_type not null,
	digest varchar(255) not null,
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP
);

create table if not exists user_roles (
	id integer primary key default nextval('hibernate_sequence'),
	userid integer not null references users(id),
	roleid integer not null references roles(id)
);

create table user_status(
    id integer primary key not null default nextval('hibernate_sequence'), 
    userid integer not null references users(id),
    
    locked bool default false,
    verified bool default false,
    
    last_login timestamptz,
    last_action timestamptz,
    migrated timestamptz,
    
    failed_logins integer default 0
);

create table user_data(
	id integer primary key default nextval('hibernate_sequence'),
	userid integer references users(id),
	
	signature varchar(1024),
	avatar varchar(255),
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP	
);

create table apiusers (
    id integer primary key default nextval('hibernate_sequence'),
	login varchar(255) not null unique,
	token varchar(1024),
	
	valid_until timestamptz,
	active bool not null default false,
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP	
);
