FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-21-jdk && \
    apt-get clean;

WORKDIR /app

COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:21-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]