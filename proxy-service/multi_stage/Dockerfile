FROM maven:3.6.3-jdk-8-slim as BUILD

COPY . /usr/src/proxy-service
WORKDIR /usr/src/proxy-service

RUN mvn -s /usr/share/maven/ref/settings-docker.xml -DskipTests package

FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
EXPOSE 9090 9090
COPY --from=BUILD /usr/src/proxy-service/target/proxy-service-*.jar /opt/target/proxy-service.jar
WORKDIR /opt/target
CMD java -Dmicronaut.environments=docker -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar proxy-service.jar