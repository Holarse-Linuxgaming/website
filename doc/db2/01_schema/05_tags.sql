create table taggroups(
	id integer primary key default nextval('hibernate_sequence'),
	
	name varchar(255),
	weight integer,
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id)	
);

create table tags(
	id integer primary key default nextval('hibernate_sequence'),
	name varchar(255) not null,
	name_lang varchar(12) default 'english',
	weight integer,
	use_count integer default 0,
	aliasid integer references tags(id),
	taggroupid integer references taggroups(id),
	
	drupalid integer,
	
	created timestamptz not null default CURRENT_TIMESTAMP,
	updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id)	
);

-- Die Tags aller Nodes
create table node_tags(
    id integer primary key default nextval('hibernate_sequence'),    

    tagid integer references tags(id),
    nodeid integer not null,

    created timestamptz not null default CURRENT_TIMESTAMP,
    updated timestamptz not null default CURRENT_TIMESTAMP,
    update_userid integer references users(id)	
);

-- Trigger für den automatischen Tag-Zähler
CREATE OR REPLACE FUNCTION update_tag_use_count() RETURNS TRIGGER AS $trg_tag_usecount$
   BEGIN
      UPDATE tags SET use_count = (SELECT count(1) FROM node_tags WHERE tagid = new.tagid) WHERE id = new.tagid;
      RETURN NEW;
   END;
$trg_tag_usecount$ LANGUAGE plpgsql;
ALTER FUNCTION update_tag_use_count() OWNER TO holarse;

DROP TRIGGER IF EXISTS trg_tag_usecount ON articles_tags CASCADE;
CREATE TRIGGER trg_tag_usecount AFTER INSERT OR DELETE ON articles_tags
FOR EACH ROW EXECUTE PROCEDURE update_tag_use_count();