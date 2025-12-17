begin transaction;

-- Wir frieren die Tags eines Artikels zum Speicherzeitpunkt ein
alter table article_revisions add tagslist jsonb;

commit;
