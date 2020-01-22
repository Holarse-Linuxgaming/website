update tags set taggroup_id = (select id from taggroups where name = 'PORTER')
where name in (
    'Asypr',
	'Feral Interactive',
	'flibitijibibo',
	'icculus',
	'Loki',
	'Virtual Programming',
	'Hyperion Entertainment',
	'Runesoft'
);