update tags set taggroup_id = (select id from taggroups where name = 'LICENSE')
where name in (
    'Kommerziell',
    'Freeware',
    'Open Source',
    'drm-frei',
    'Free2Play',
    'DRM',
    'in-app-purchases',
    'Ingame-Store',
    'region-locked',
    'Pay2Win',
    'Aktivierung',
    'Adware',
    'Abonnement',
    'Account notwendig',
    'gratis',
    'kickstarter',
    'PayWhatYouWant'
);
