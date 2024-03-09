# Stage 1: Build the application
FROM adoptopenjdk:21-jdk-hotspot AS builder

WORKDIR /app

COPY . .

RUN ./gradlew clean build -x test

# Stage 2: Create the final image
FROM adoptopenjdk:21-jre-hotspot

WORKDIR /app

COPY --from=builder /app/build/libs/utopia.api.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]