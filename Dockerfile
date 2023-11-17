FROM ubuntu:latest AS build
RUN apt-get update
RUN apt-get install openjdk-19-jdk -y
COPY . .
RUN ./gradlew bootJar --no-daemon

FROM openjdk:19-jdk-slim
EXPOSE 8080

COPY ./public /public
COPY --from=build /build/libs/utopia.api-1.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]