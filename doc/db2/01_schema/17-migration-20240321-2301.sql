begin transaction;

-- set migration version
update migration_version set version='20240321-2301';

-- News brauchen auch einen Teaser
alter table news_revisions add teaser varchar(512);
alter table news_revisions add teaser_lang varchar(12) default 'german';

commit;
