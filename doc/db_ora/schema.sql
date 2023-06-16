create sequence holarse_seq start with 1;

-- roles
create table roles (
id integer default on null holarse_seq.nextval,
code varchar2(255) not null,
access_level integer default on null 999,
created timestamp with time zone default on null CURRENT_TIMESTAMP,
updated timestamp with time zone default on null CURRENT_TIMESTAMP,
constraint code_unique unique(code),
check (access_level > 0));

-- user-status
create table user_status(
    id integer default on null holarse_seq.nextval primary key,
    locked number(1,0) default on null 0,
    verified number(1,0) default on null 0,
    verification_hash varchar2(255),
    verification_hash_validuntil timestamp with time zone,

    last_login timestamp with time zone,
    last_action timestamp with time zone,
    migrated timestamp with time zone,

    failed_logins integer default on null 0,
    created timestamp with time zone default on null CURRENT_TIMESTAMP,
    updated timestamp with time zone default on null CURRENT_TIMESTAMP
);

create table user_data(
    id integer default on null holarse_seq.nextval primary key,

    signature varchar2(1024),
    avatar varchar2(255),

    created timestamp with time zone default on null CURRENT_TIMESTAMP,
    updated timestamp with time zone default on null CURRENT_TIMESTAMP
);

create table users (
    id integer default on null holarse_seq.nextval primary key,
    login varchar2(255) not null,
    email varchar2(255) not null,

    drupalid integer,

    user_status_id integer not null,
    user_data_id integer not null,

    hashtype varchar2(255) not null,
    digest varchar2(255) not null,

    constraint login_unique unique(login),
    constraint email_unique unique(email),

    constraint hashtype_check check(hashtype in ('bcrypt', 'md5')),
    foreign key(user_status_id) references user_status(id),
    foreign key(user_data_id) references user_data(id)
);

