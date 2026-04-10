#!/bin/bash
USER=$1

if [ -z "$USER" ]; then
    echo "Missing username"
    exit 1
fi

docker compose exec db psql -v ON_ERROR_STOP=1 -U holarse -d holarse -c "insert into user_roles(user_id, role_id) values ((select id from users where login = '$USER'), (select id from roles where code = 'ADMIN'));"
RC=$?
if [ "$RC" -ne 0 ]; then
    echo "uplifting of $USER failed"
    exit 2
fi
exit 0