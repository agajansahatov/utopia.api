# Stage 1: Build the application
FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get clean;

WORKDIR /app

COPY . .

RUN ./gradlew bootJar --no-daemon

# Stage 2: Create the final image
FROM openjdk:11-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/build/libs/utopia.api-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]
