FROM node:20 AS node-build
WORKDIR /build
COPY . .
RUN npm install && npm run build

FROM maven:3.9-eclipse-temurin-21-alpine AS builder
WORKDIR /build
COPY --from=node-build /build .
RUN mvn package -DskipTests

FROM openjdk:21-jdk
WORKDIR /app
COPY --from=builder /build/target/*.jar moex-app.jar
EXPOSE 8083
EXPOSE 8093
ENTRYPOINT ["java","-jar","moex-app.jar"]