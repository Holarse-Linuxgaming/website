default: build

build:
    cd app && mvn clean package

clean:
    rm -rf app/target

rebuild: build app-down app-up 

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

app-down:
	docker compose stop app

app-up:
	docker compose up app -d