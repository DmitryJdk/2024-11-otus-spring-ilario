FROM openjdk:21-jdk
ARG JAR_FILE=target/hw17-docker-0.0.1-SNAPSHOT.jar
COPY ${JAR_FILE} app.jar
EXPOSE 8080
EXPOSE 8090
ENTRYPOINT ["java","-jar","/app.jar"]