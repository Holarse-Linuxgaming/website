create table if not exists messages (
	id integer primary key default nextval('hibernate_sequence'),
	sender_userid integer references users(id),
	title varchar(255),
	content varchar(4096),
	
	draft bool not null default false, 
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP		
);

create table if not exists message_recipients(
	id integer primary key default nextval('hibernate_sequence'),
	messageid integer references messages(id),
	recipient_userid integer references users(id)
);
