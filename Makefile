up:
	docker compose up

down:
	docker compose down

logs:
	docker compose logs -f

build:
	docker run -it --rm -u ubuntu -v ~/.m2:/home/ubuntu/.m2 -v.:/opt/build -e MAVEN_CONFIG=/home/ubuntu/.m2 -w /opt/build/app/ maven:3-eclipse-temurin-21 mvn clean package

build-container:
	docker compose build app

clean:
	$(RM) -rf app/target
	docker compose rm app

restart:
	docker compose restart app

open:
	xdg-open http://localhost:8080/holarseweb

status:
	docker compose -f doc/docker-compose.yml ps

shell:
	docker compose -f doc/docker-compose.yml exec -it app /bin/bash
