begin transaction;

-- set migration version
update migration_version set version='20240222-2250';

-- Interne Downloads und Interne Tool-Downloads per Attachment
alter type attachment_data_type add value 'yes_or_no';
insert into attachment_groups (code, label) values ('internal', 'Internes');

insert into attachment_types (code, label, attachment_group_id, datatype) values
	('internalfs', 'Fileserver', (select id from attachment_groups where code = 'internal'), 'yes_or_no');

insert into attachment_types (code, label, attachment_group_id, datatype) values
	('internalfs-tools', 'Fileserver Tools', (select id from attachment_groups where code = 'internal'), 'yes_or_no');

commit;