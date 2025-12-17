begin transaction;

-- Interne Downloads und Interne Tool-Downloads per Attachment
insert into attachment_groups (code, label) values ('internal', 'Internes');

insert into attachment_types (code, label, attachment_group_id, datatype) values
	('internalfs', 'Fileserver', (select id from attachment_groups where code = 'internal'), 'yes_or_no');

insert into attachment_types (code, label, attachment_group_id, datatype) values
	('internalfs-tools', 'Fileserver Tools', (select id from attachment_groups where code = 'internal'), 'yes_or_no');

commit;

