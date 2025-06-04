FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /build/target/*.jar moex-integration.jar
EXPOSE 8081
EXPOSE 8091
ENTRYPOINT ["java","-jar","moex-integration.jar"]