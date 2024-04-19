create table roles(
	id integer primary key default nextval('hibernate_sequence'),
	code varchar(255) not null unique,
	
	access_level integer check (access_level > 0),
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP
);

create type password_type as enum ('md5', 'bcrypt');

create table user_status(
    id integer primary key not null default nextval('hibernate_sequence'),

    locked bool default false,
    verified bool default false,
    verification_hash varchar(255),
    verification_hash_validuntil timestamptz,

    last_login timestamptz,
    last_action timestamptz,
    migrated timestamptz,
    imported timestamptz,

    failed_logins integer default 0,

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP
);

create table user_data(
	id integer primary key default nextval('hibernate_sequence'),

	signature varchar(1024),
	avatar varchar(255),

	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP
);

create table users (
	id integer primary key default nextval('hibernate_sequence'),
	
	login varchar(255) not null unique,
	email varchar(255) not null unique,
	
	drupalid integer,
	
	hashtype password_type not null,
	digest varchar(255) not null,

	user_status_id integer references user_status(id),
	user_data_id integer references user_data(id),
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP
);

create table user_roles (
	user_id integer not null references users(id),
	role_id integer not null references roles(id)
);

create index idx_user_verification_hash on user_status (verification_hash, locked, verified);

create table apiusers (
    id integer primary key default nextval('hibernate_sequence'),
    login varchar(255) not null unique,
    rolename varchar(255),
    token varchar(1024),

    valid_until timestamptz,
    active bool not null default false,

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP	
);
