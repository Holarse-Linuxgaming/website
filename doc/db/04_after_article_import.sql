--------
-- This script can be run by an admin user (e.g. postgres)
-- or the holarse user and should be used, after the articles
-- has been imported.
--------

-- Variables for the script
\SET dbName holarse
\SET dbUser holarse
\SET apiUser dummy
--- API Password: geheim
\SET apiPassword ADDB0F5E7826C857D7376D1BD9BC33C0C544790A2EAC96144A8AF22B1298C940


-- 1. Set the DB to holarse
\connect -reuse-previous=on :dbName;


-- 2. Engine tags
\echo '-- Update engine tags --'
UPDATE tags SET taggroup_id = (SELECT id FROm taggroups WHERE name = 'ENGINE')
WHERE name IN (
    'Adobe AIR',
    'AGS',
    'Allegro',
    'Build Engine',
    'EDuke32',
    'FIFEngine',
    'Flash',
    'FNA',
    'Game Maker',
    'godot',
    'HTML5',
    'Irrlicht',
    'Ruby',
    'Java',
    'jMonkeyEngine',
    'jMonkeyVR',
    'Haxeflixel',
    'libgdx',
    'löve',
    'LWJGL',
    'Mono',
    'MonoGame',
    '.NET Core',
    'OpenFL',
    'OpenGL',
    'plib',
    'PyGame',
    'Python',
    'ResidualVM',
    'RPG Maker',
    'SDL',
    'Source-Engine',
    'Stratagus',
    'Torque3D',
    'Unity',
    'Unreal Engine 3',
    'Unreal Engine 4',
    'XNA',
    'zdoom',
    'OGRE3D',
    'Openra',
    'Panda3D'
);


-- 3. Franchise tags
\echo '-- Update franchise tags --'
UPDATE tags SET taggroup_id = (SELECT id FROM taggroups WHERE name = 'FRANCHISE')
WHERE name IN (
    'Anno',
    'assasin',
    'Caesar',
    'Command & Conquer',
    'Descent',
    'Doom',
    'Elite',
    'Final Fantasy',
    'GTA',
    'Lemmings',
    'quake3',
    'SAGE',
    'Sauerbraten',
    'Siedler',
    'spring',
    'Star Trek',
    'star wars',
    'Tomb Raider',
    'Total War',
    'Tropico',
    'Warhammer 40k',
    'Wolfenstein',
    'X-Universum'
);


-- 4. Licence tags
\echo '-- Update licence tags --'
UPDATE tags SET taggroup_id = (SELECT id FROM taggroups WHERE name = 'LICENSE')
WHERE name IN (
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


-- 5. Multiplayer tags (with more multiplayer!)
\echo '-- Update multiplayer tags --'
UPDATE tags SET taggroup_id = (SELECT id FROM taggroups WHERE name = 'MULTIPLAYER')
WHERE name IN (
    '10+Spieler',
    '10-Spieler',
    '10-Spieler',
    '1-255 Spieler LAN',
    '1-255 Spieler Online',
    '12-Spieler',
    '15-Spieler vs online',
    '16-Spieler',
    '16-Spieler co-op online',
    '200-Spieler',
    '20-Spieler',
    '2-Spieler',
    '2-Spieler lan',
    '2-Spieler Lokal',
    '32-Spieler',
    '3-Spieler',
    '3-Spieler Lokal',
    '4-Spieler',
    '4-spieler lan',
    '4-Spieler Lokal',
    '4-Spieler Online',
    '5-Spieler',
    '5-Spieler Lokal',
    '64-Spieler',
    '6-Spieler',
    '6-spieler-lan',
    '7-Spieler',
    '8-Spieler',
    '8-spieler-lan',
    'bis zu 12 Spieler',
    'bis zu 20 Spieler',
    'bis zu 2 Spieler',
    'bis zu 3 Spieler',
    'bis zu 4 Spieler',
    'bis zu 8 Spieler',
    'Coop',
    'Co-Op',
    'Crossplattform',
    'Plattformspezifisch',
    'dedicated server',
    'Einzelspieler',
    'gut spielbar zu 10',
    'gut spielbar zu 11',
    'gut spielbar zu 12',
    'gut spielbar zu 13',
    'gut spielbar zu 14',
    'gut spielbar zu 15',
    'gut spielbar zu 16',
    'gut spielbar zu 17',
    'gut spielbar zu 18',
    'gut spielbar zu 19',
    'gut spielbar zu 2',
    'gut spielbar zu 20',
    'gut spielbar zu 3',
    'gut spielbar zu 4',
    'gut spielbar zu 5',
    'gut spielbar zu 6',
    'gut spielbar zu 7',
    'gut spielbar zu 8',
    'gut spielbar zu 9',
    'kein LAN',
    'keinLAN',
    'LAN',
    'mehrspieler',
    'Multiplayer',
    'Multiplayer Lokal',
    'Netzwerk',
    'online',
    'Online Game',
    'splitscreen',
    'split-screen'
);


-- 6. Platform tags
\echo '-- Update plattform tags --'
UPDATE tags SET taggroup_id = (SELECT id FROM taggroups WHERE name = 'PLATFORM')
WHERE name IN (
    'native',
    'amd64',
    '32bit',
    'Browser',
    'Wine',
    'eON',
    'DOSBox',
    'Crossover',
    'Crossover Games',
    'DXVK',
    'Proton',
    'ScummVM',
    'Wine-Port',
    'Pointrelease',
    'Playonlinux',
    'DOS',
    'MS-DOS',
    'DOSBOX',
    'Linux/PPC',
    'C64',
    'Raspberry Pi',
    'Android',
    'snes',
    'Alpha',
    'Nintendo',
    'Amiga'
);


-- 7. Store tags
\echo '-- Update store tags --'
UPDATE tags SET taggroup_id = (SELECT id FROM taggroups WHERE name = 'STORE')
WHERE name IN (
    'deliver2',
    'GOG',
    'Steam',
    'Humble Bundle',
    'Bundle Store',
    'Humble Widget',
    'itch.io'
);


-- 8. Genre tags
\echo '-- Update genre tags --'
UPDATE tags SET taggroup_id = (SELECT id FROM taggroups WHERE name = 'GENRE')
WHERE name IN (
    'Arcade',
    'Shooter',
    'Ego-Shooter',
    'Action',
    'Echtzeitstrategie',
    'Adventure',
    'Horror',
    'RTS',
    'Strategie',
    'Weltraum',
    'Wirtschaftssimulation',
    'Rundenstrategie',
    'MMORPG',
    'Rollenspiele',
    'Zombie',
    'Flugsimulator',
    'Puzzle',
    'Städtebau',
    'Simulation',
    'Tower Defense',
    'FPS',
    'Landwirtschaft', 
    'Wikinger',
    'Musik',
    'Fußball',
    'Open World',
    'Post-Apokalypse',
    'Point and Click',
    'City Builder',
    'Action-Adventure',
    'Aufbaustrategie',
    'Taktik',
    'Sci-Fi',
    'Third Person Shooter',
    'Pinball',
    'Flipper',
    'Fantasy',
    'Rogue-like',
    'Run and gun',
    'Lernen',
    'Oldschool',
    'jump''n''run',
    'platformer',
    'Casual',
    'Voxel',
    'Visual Novel',
    'rundenbasiert',
    'Shoot''em Up',
    'Sandbox',
    '4X-Strategie',
    'Weltraumsimulation',
    'Survival',
    'Golf',
    'Minigolf',
    'crafting',
    'Detektivspiel',
    'Piraten',
    'manga',
    'MMORTS',
    'Krieg',
    'Brettspiele',
    'Kampf',
    '2D Side-Scrolling',
    'Grand Strategy',
    'Mittelalter',
    'dungeon crawler',
    'Kartenspiel',
    'Musikspiel',
    'Retro',
    'JRPG',
    'Abenteuer',
    'Management',
    'MOBA',
    'Gelegenheitsspiel',
    'Politiksimulation',
    'Gesellschaftsspiel',
    'Artillery',
    'Metroidvania',
    'Geschicklichkeit',
    'Hack''n''Slash',
    'Beat''em''up',
    'Stealth',
    'Einbruch',
    'Verkehrssimulation',
    'Hacker',
    'Anime',
    'Sport',
    'Transport',
    'Schiffssimulation',
    'Schiffe',
    'Dating Sim',
    'Top-Down',
    'Burgenbau',
    'Unterwasser',
    'Strategie',
    'MMOFPS',
    'BridgeSim',
    'Rail-Shooter',
    'Fighting',
    'Logik',
    'Karten',
    'Interactive Book',
    '2. Weltkrieg',
    '1. Weltkrieg',
    'rhythmus',
    'rhythm',
    'Walking Simulator',
    'Krimi',
    'Hard Sci-Fi',
    'Splatter',
    'dungeon',
    'Baustelle',
    'Dinosaurier',
    'TV',
    'looten und leveln',
    'Medizin',
    'Coding',
    'Kolonisierung',
    'Ausmalen',
    'D&D',
    'Third Person',
    'Interactive Tale', 
    'Western',
    'Permadeath',
    'Blöcke',
    'Rätsel',
    'Tycoon',
    'infinite runner',
    'Baseball',
    'Space Combat',
    'Wimmelbild',
    'Winter',
    'Steampunk',
    'Sportsimulation',
    'Racing',
    'Warhammer', 
    'Noir',
    'Mythologie',
    'Godlike',
    'God',
    'Brückenbau',
    'Dystopia',
    'Rally',
    'Gore',
    'Soccer',
    'Cards',
    'Helikopter',
    'Militär',
    'Hubschrauber',
    'Violent',
    'Motocross',
    'Motorrad',
    'Football',
    'Kochen',
    'Erotik',
    'Table-Top',
    'Ameisen',
    'Kindgerecht',
    'Relaxing',
    'Würfelspiel',
    'Feuerwehr',
    'Bildung',
    'Cyberpunk',
    'Roboter',
    'Robots',
    'Battle Royale',
    'U-Boot',
    'rogue-lite',
    'Party',
    'run''n''gun',
    'Surreal',
    'Strategy',
    'Schleichen',
    'Escape',
    'Witzig',
    'Wilder Westen',
    'Mystery',
    'Gewalt',
    'Off-Road',
    'Suchspiel',
    'Angeln',
    'Fischen',
    'Drama',
    'Samurai',
    'Raumstation',
    'Vietnam',
    'Krankenhaus',
    'Zeitreisen', 
    'Züge',
    'First Person',
	'historisch', 
	'Rennspiele',
	'Sportspiele',
	'Weltraumshooter',
	'Kinder',
	'Fussball Manager',
	'Skating',
	'Social',
	'infinte runner',
	'boxen',
	'isometrisch',
	'Exploration',
	'Rom',
	'Schwer',
	'generierter Inhalt',
	'Wuselfaktor',
	'text-basiert',
	'Mech', 
	'Meditation',
	'MMO',
	'Procedural Generated',
	'Programmierung',
	'Psycho',
	'RPG',
	'Stategie',
	'terminal',
	'Pixelart',
	'pixelgrafik'
);


-- 9. Porter tags
\echo '-- Update porter tags --'
UPDATE tags SET taggroup_id = (SELECT id FROM taggroups WHERE name = 'PORTER')
WHERE name IN (
    'Asypr',
	'Feral Interactive',
	'flibitijibibo',
	'icculus',
	'Loki',
	'Virtual Programming',
	'Hyperion Entertainment',
	'Runesoft'
);


-- 10. Package Manager tags
\echo '-- Update package manager tags --'
UPDATE tags SET taggroup_id = (SELECT id FROM taggroups WHERE name = 'PACKAGEMANAGER')
WHERE name IN (
    'PPA',
	'SNAP',
	'Flatpak',
	'AppImage',
);



-- 11. Adjustments for the articles and tags (must be run as last instruction!)
\echo '-- Adjustments for articles and tags --'
--- Rename tags
UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'Kommerziell')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'Kommerztiell');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'Kommerziell')
WHERE tags_id = (SELECT id FROm tags WHERE name = 'Kommerzille');

UPDATE tags SET name = 'infinite runner' WHERE name = 'infinte runner';

UPDATE tags SET name = 'Mehrspieler' WHERE name = 'mehrspieler';

--- Set tags correctly
UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = '32bit')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'native_x86');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'amd64')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'native_x86_64');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'einstellt')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'einstellt. eingestampft');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'Flatpack')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'Flatpak');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'Gamepad')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'Gampad');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'kein LAN')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'keinLAN');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'Mod')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'Mods');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'Steam VR')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'SteamVR');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'split-screen')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'splitscreen');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'Strategie')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'Stategie');

UPDATE articles_tags
SET tags_id = (SELECT id FROM tags WHERE name = 'Engines')
WHERE tags_id = (SELECT id FROM tags WHERE name = 'engine');


--- Tag assignment
INSERT INTO tags(id, created, name, taggroup_id)
VALUES
    (nextval('hibernate_sequence'), current_timestamp, 'Feral Store', (SELECT id FROM taggroups WHERE name = 'STORE'));

INSERT INTO articles_tags(article_id, tags_id)
    SELECT a.id AS article_id, (SELECT id FROM tags WHERE name = 'Feral Store') AS tags_id
    FROM articles a 
    LEFT JOIN articles_tags at1 ON at1.article_id = a.id AND at1.tags_id = (SELECT id FROM tags WHERE name = 'Feral Interactive')
    LEFT JOIN articles_tags at2 ON at2.article_id = a.id AND at2.tags_id = (SELECT id FROM tags WHERE name = 'Spiele')
    LEFT JOIN articles_tags at3 ON at3.article_id = a.id AND at3.tags_id = (SELECT id FROM tags WHERE name = 'Kommerziell')
    WHERE at1.tags_id IS NOT null AND at2.tags_id IS NOT null AND at3.tags_id IS NOT null;


-- End
\echo '----'
\echo 'Done!'
