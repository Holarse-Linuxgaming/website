up:
	docker compose -f doc/docker-compose.yml up -d

down:
	docker compose -f doc/docker-compose.yml down

logs:
	docker compose -f doc/docker-compose.yml logs -f

build:
	docker run -it --rm -u ubuntu -v ~/.m2:/home/ubuntu/.m2 -v.:/opt/app -e MAVEN_CONFIG=/home/ubuntu/.m2 -w /opt/app/ maven:3-eclipse-temurin-21 mvn clean package

restart:
	docker compose -f doc/docker-compose.yml restart app

open:
	xdg-open http://localhost:8080/holarseweb

status:
	docker compose -f doc/docker-compose.yml ps

shell:
	docker compose -f doc/docker-compose.yml exec -it app /bin/bash