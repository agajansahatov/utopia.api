FROM openjdk:17-slim

WORKDIR /app

COPY . .

RUN gradlew build

COPY build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]