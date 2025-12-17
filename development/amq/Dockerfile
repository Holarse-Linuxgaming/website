FROM opensuse/leap:15.6
ARG AMQ_VERSION=2.38.0

ENV ARTEMIS_USER holarse
ENV ARTEMIS_PASSWORD geheim
ENV ANONYMOUS_LOGIN false

RUN zypper ref && zypper in -y java-21-openjdk-headless wget tar gzip libaio
RUN mkdir -p /opt/
WORKDIR /opt/
RUN wget "https://www.apache.org/dyn/closer.cgi?filename=activemq/activemq-artemis/${AMQ_VERSION}/apache-artemis-${AMQ_VERSION}-bin.tar.gz&action=download" -O apache-artemis-${AMQ_VERSION}-bin.tar.gz
RUN tar xvzf apache-artemis-${AMQ_VERSION}-bin.tar.gz
RUN /opt/apache-artemis-${AMQ_VERSION}/bin/artemis create --no-amqp-acceptor --no-hornetq-acceptor --no-mqtt-acceptor --no-stomp-acceptor --home /opt/apache-artemis-${AMQ_VERSION}/ --user ${ARTEMIS_USER} --password ${ARTEMIS_PASSWORD} --queues drueckblick,imports,mail,search --http-host 0.0.0.0 --relax-jolokia --require-login holaq

EXPOSE 8161
EXPOSE 61616

VOLUME /opt/holaq/data/journal

CMD /opt/holaq/bin/artemis run
