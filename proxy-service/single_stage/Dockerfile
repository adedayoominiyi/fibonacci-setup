# FROM adoptopenjdk/openjdk11-openj9:jdk-11.0.1.13-alpine-slim
FROM openjdk:8u212-jre-alpine
COPY target/proxy-service-*.jar proxy-service.jar
EXPOSE 9090 9090
CMD java -Dmicronaut.environments=docker -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar proxy-service.jar