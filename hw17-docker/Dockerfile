FROM maven:3.9-eclipse-temurin-17 AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /build/target/*.jar app.jar
EXPOSE 8080
EXPOSE 8090
ENTRYPOINT ["java","-jar","app.jar"]