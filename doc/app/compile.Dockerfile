FROM maven:3.9-eclipse-temurin-21

VOLUME [ "/opt/app" ]
VOLUME [ "/root/.m2" ]

RUN mkdir -p /opt/app
WORKDIR /opt/app

CMD [ "/usr/bin/mvn", "clean", "package" ]