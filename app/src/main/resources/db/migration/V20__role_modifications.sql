delete from user_roles where role_id = (select id from roles where code = 'TRUSTED_USER');
delete from roles where code = 'TRUSTED_USER';
update roles set code = 'CORE' where code = 'HOLARSE-CORE';
