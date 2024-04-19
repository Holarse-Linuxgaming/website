#!/bin/bash

PG_PASSWORD=geheim
USER=$1

docker-compose -f doc/docker-compose.yml exec -T db psql -v ON_ERROR_STOP=1 -U holarse -d holarse -c "insert into user_roles(user_id, role_id) values ((select id from users where login = '$USER'), (select id from roles where code = 'ADMIN'));"
