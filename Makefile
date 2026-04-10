DOCKER_OPTS=--env-file development/db.env --env-file development/oci.env

default: rebuild

build: build-frontend build-backend

build-backend:
	$(MAKE) -C app $@

build-frontend:
	yarn sass app/src/main/resources/assets/scss/holarse.scss:app/src/main/resources/assets/css/holarse.css

rebuild: build app-down app-up 

clean:
	$(MAKE) -C app $@

up:
	docker compose up -d

logs:
	docker compose logs -f

down:
	docker compose down

shell:
	docker compose -f docker-compose.yml exec -it app /bin/bash

status:
	docker compose -f docker-compose.yml ps

setup:
	mise install
	yarn install

app-down:
	docker compose stop app

app-up:
	docker compose up app -d

db-dump:
	docker compose exec db pg_dump -U holarse holarse > ./backup/holarse-$(shell date +"%Y-%m-%d-%H%M%S%z").sql

db-reindex:
	docker compose exec db reindexdb -U holarse --all
	docker compose exec db psql -U holarse -A holarse -c "ALTER DATABASE holarse REFRESH COLLATION VERSION;"
	docker compose exec db psql -U holarse -A holarse -c "REINDEX DATABASE;"
