compile-and-restart: stop-app build-app build-container up-app
	
up:
	docker compose up

down:
	docker compose down

logs:
	docker compose logs -f

stop-app:
	docker compose stop app

up-app:
	docker compose up app -d

build-app:
	docker run -it --rm -u ubuntu -v ~/.m2:/home/ubuntu/.m2 -v.:/opt/build -e MAVEN_CONFIG=/home/ubuntu/.m2 -w /opt/build/app/ maven:3-eclipse-temurin-25 mvn clean package

build-container: build-app
	docker compose build app

clean:
	$(RM) -rf app/target
	docker compose rm app

db-migrate:
	docker compose up db -d
	docker run -it --rm --add-host=host.docker.internal:host-gateway --env-file development/db.env --env POSTGRES_URL=jdbc:postgresql://host.docker.internal:5432/holarse -u ubuntu -v ~/.m2:/home/ubuntu/.m2 -v.:/opt/build -e MAVEN_CONFIG=/home/ubuntu/.m2 -w /opt/build/app/ maven:3-eclipse-temurin-25 mvn flyway:migrate
	docker compose down db

restart:
	docker compose restart app

open:
	xdg-open http://localhost:8080/holarseweb

status:
	docker compose -f doc/docker-compose.yml ps

shell:
	docker compose -f doc/docker-compose.yml exec -it app /bin/bash
