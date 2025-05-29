FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY . .
RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /build/target/*.jar moex-information.jar
EXPOSE 8082
EXPOSE 8092
ENTRYPOINT ["java","-jar","moex-information.jar"]