begin transaction;

-- News brauchen auch einen Teaser
alter table news_revisions add teaser varchar(512);
alter table news_revisions add teaser_lang varchar(12) default 'german';

commit;
