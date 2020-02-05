FROM openjdk:8u212-jre-alpine
COPY target/fibonacci-server-1.0-SNAPSHOT-jar-with-dependencies.jar fibonacci-server-1.0-SNAPSHOT-jar-with-dependencies.jar
EXPOSE 9091 9091
CMD java -Dcom.sun.management.jmxremote -noverify ${JAVA_OPTS} -jar fibonacci-server-1.0-SNAPSHOT-jar-with-dependencies.jar