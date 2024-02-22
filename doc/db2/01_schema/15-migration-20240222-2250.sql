begin;

create table migration_version(version varchar(255));

-- set migration version
update migration_version set version='20240222-2250';

-- render cache
create type render_cache_type as enum ('empty',     -- Einfach nur leer
                                       'unchanged', -- Ohne Anpassungen
                                       'web',       -- Zur Darstellung im Browser
                                       'search',    -- Darstellung ohne Syntax für die Suche
                                       'preview'    -- Darstellung für die Vorschau
);


create table render_cache(
	id integer primary key default nextval('hibernate_sequence'),
        node_id integer,
        render_type render_cache_type,
        content varchar(16384),

	created timestamptz not null default CURRENT_TIMESTAMP,
        updated timestamptz not null default CURRENT_TIMESTAMP,
        unique(node_id, render_type)
);

commit;
