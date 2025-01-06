begin transaction;

-- set migration version
update migration_version set version='20250106-1449';

-- Wir frieren die Tags eines Artikels zum Speicherzeitpunkt ein
alter table article_revisions add tagslist jsonb;

commit;
