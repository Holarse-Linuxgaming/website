update tags set taggroup_id = (select id from taggroups where name = 'STORE')
where name in (
    'deliver2',
    'GOG',
    'Steam',
    'Humble Bundle',
    'Bundle Store',
    'Humble Widget',
    'itch.io'
);
