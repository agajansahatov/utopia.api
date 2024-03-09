FROM ubuntu:latest AS build

RUN apt-get update && \
    apt-get install -y openjdk-11-jdk && \
    apt-get clean;

WORKDIR /app

COPY . .

RUN ./gradlew bootJar --no-daemon

FROM openjdk:11-jdk-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/build/libs/utopia.api-1.jar app.jar
COPY ./public /public
COPY ./public/images/products/p1.jpg /public/images/products/p1.jpg
COPY ./application.properties /application.properties

ENTRYPOINT ["java", "-jar", "app.jar"]