# Use an official OpenJDK runtime as a parent image
FROM openjdk:19-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY ./build/libs/utopia.api-0.0.1-SNAPSHOT.jar app.jar

# Expose the port that your Spring Boot application runs on
EXPOSE 8080

# Define the command to run your application
CMD ["java", "-jar", "app.jar"]
