-- Ergänzung der Artikel-Titel um die Tokenizer-Sprache, gültige Werte: 'english' oder 'german'
ALTER TABLE public.articles ADD COLUMN alternativetitle4 character varying(255);
ALTER TABLE public.articles ADD COLUMN alternativetitle5 character varying(255);
ALTER TABLE public.articles ADD COLUMN title_lang character varying(12) default 'english';
ALTER TABLE public.articles ADD COLUMN alternativetitle1_lang character varying(12) default 'english';
ALTER TABLE public.articles ADD COLUMN alternativetitle2_lang character varying(12) default 'english';
ALTER TABLE public.articles ADD COLUMN alternativetitle3_lang character varying(12) default 'english';
ALTER TABLE public.articles ADD COLUMN alternativetitle4_lang character varying(12) default 'english';
ALTER TABLE public.articles ADD COLUMN alternativetitle5_lang character varying(12) default 'english';
ALTER TABLE public.articles ADD COLUMN content_lang character varying(12) default 'german';

ALTER TABLE public.news ADD COLUMN title_lang character varying(12) default 'german'; 
ALTER TABLE public.news ADD COLUMN subtitle_lang character varying(12) default 'german';
ALTER TABLE public.news ADD COLUMN content_lang character varying(12) default 'german';

ALTER TABLE public.comments ADD COLUMN content_lang character varying(12) default 'german';