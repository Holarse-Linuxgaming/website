DOCKER_OPTS=--env-file development/db.env --env-file development/oci.env --env-file development/queue.env
NODE_MODULES=node_modules/
ASSETS=app/src/main/resources/assets
SASS_OPTS=--load-path=node_modules --no-source-map --style=compressed --quiet-deps 

default: rebuild

build: build-frontend build-backend

build-backend: copy-dependencies
	$(MAKE) -C app $@

build-frontend: copy-dependencies setup-yarn
	yarn sass $(SASS_OPTS) app/src/main/resources/assets/scss/holarse.scss:app/src/main/resources/assets/css/holarse.min.css

rebuild: build app-down app-up 

clean:
	$(MAKE) -C app $@

clean-yarn:
	$(RM) -rf .yarn
	$(RM) .pnp.cjs
	$(RM) .pnp.loader.mjs
	$(RM) $(ASSETS)/css/*
	$(RM) $(ASSETS)/js/*.min.js
	$(RM) $(ASSETS)/js/de-de.js
	$(RM) $(ASSETS)/fonts/*

copy-dependencies:
	# bootstrap css is included in sass build
	cp $(NODE_MODULES)/bootstrap/dist/js/bootstrap.min.js $(ASSETS)/js/
	cp $(NODE_MODULES)/@popperjs/core/dist/umd/popper.min.js $(ASSETS)/js/
	cp $(NODE_MODULES)/charts.css/dist/charts.min.css $(ASSETS)/css/
	cp $(NODE_MODULES)/filepond/dist/filepond.min.css $(ASSETS)/css/
	cp $(NODE_MODULES)/filepond/dist/filepond.min.js $(ASSETS)/js/
	cp $(NODE_MODULES)/filepond/locale/de-de.js $(ASSETS)/js/
	
	cp $(NODE_MODULES)/bootstrap-icons/font/bootstrap-icons.min.css $(ASSETS)/css/
	cp $(NODE_MODULES)/bootstrap-icons/font/fonts/* $(ASSETS)/css/fonts/

up:
	docker compose $(DOCKER_OPTS) up -d

logs:
	docker compose $(DOCKER_OPTS) logs -f

down:
	docker compose $(DOCKER_OPTS) down 

shell:
	docker compose $(DOCKER_OPTS) exec -it app /bin/bash

status:
	docker compose $(DOCKER_OPTS) ps

setup: setup-mise setup-yarn

setup-mise:
	mise install

setup-yarn:
	yarn install

app-down:
	docker compose $(DOCKER_OPTS) stop app

app-up:
	docker compose $(DOCKER_OPTS) up app -d

db-dump:
	docker compose exec db pg_dump -U holarse holarse > ./backup/holarse-$(shell date +"%Y-%m-%d-%H%M%S%z").sql

db-reindex:
	docker compose exec db reindexdb -U holarse --all
	docker compose exec db psql -U holarse -A holarse -c "ALTER DATABASE holarse REFRESH COLLATION VERSION;"
	docker compose exec db psql -U holarse -A holarse -c "REINDEX DATABASE;"

export-build:
	$(MAKE) -C tools/HolarseExport build
