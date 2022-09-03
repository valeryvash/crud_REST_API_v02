# Populate users
insert into users (name)
values
    ('Owen Tangney'),
    ('Lois Lane'),
    ('Kristen Stewart'),
    ('Amayak Akopyan'),
    ('Sherlock Homes'),
    ('Valery VaSh');
# Populate files
insert into files (filePath, name)
values
    ('tangney_secret_codes.txt', '/home/desktop/'),
    ('superman_identity.pdf','/docs/print/'),
    ('my_twilight_scenario.doc','/docs/scenario/'),
    ('ahalay_mahalay.txt','/krecks/pecks/fecks/'),
    ('moriarty_identity.txt', '/home/homes/123/'),
    ('passwords.txt', 'C:/docs/');
# Populate events
insert into events
(event_type, timestamp, file_id, user_id)
values
    ('CREATED',
     now(),
     (select id from files order by id limit 1 offset 0 ),
     (select id from users order by id limit 1 offset 0 )
    ),
    ('CREATED',
     now(),
     (select id from files order by id limit 1 offset 1 ),
     (select id from users order by id limit 1 offset 1 )
    ),
    ('DELETED',
     now(),
     (select id from files order by id limit 1 offset 2 ),
     (select id from users order by id limit 1 offset 2 )
    ),
    ('DELETED',
     now(),
     (select id from files order by id limit 1 offset 3 ),
     (select id from users order by id limit 1 offset 3 )
    ),
    ('CREATED',
     now(),
     (select id from files order by id limit 1 offset 4 ),
     (select id from users order by id limit 1 offset 4 )
    ),
    ('DELETED',
     now(),
     (select id from files order by id limit 1 offset 5 ),
     (select id from users order by id limit 1 offset 4 )
    );



