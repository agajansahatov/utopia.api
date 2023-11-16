# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the JAR file into the container at /app
COPY ./build/libs/utopia-api-0.0.1-SNAPSHOT-plain.jar utopia-api.jar

# Expose the port that your application will run on
EXPOSE 8080

# Specify the command to run on container startup
CMD ["java", "-jar", "utopia-api.jar"]
#CMD ["java", "-cp", "utopia-api.jar api.utopia.Application"]