FROM maven:3.6.3-jdk-8-slim as BUILD

COPY . /usr/src/fibonacci-server
WORKDIR /usr/src/fibonacci-server

RUN mvn -s /usr/share/maven/ref/settings-docker.xml -DskipTests package

FROM openjdk:8u212-jre-alpine
EXPOSE 9091 9091
COPY --from=BUILD /usr/src/fibonacci-server/target /opt/target
WORKDIR /opt/target
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar fibonacci-server-1.0-SNAPSHOT-jar-with-dependencies.jar