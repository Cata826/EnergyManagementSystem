# Use an official OpenJDK runtime as a parent image
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the jar file into the container
COPY target/ds-2020-0.0.1-SNAPSHOT.jar /app/app.jar

# Expose the application's port
EXPOSE 8082

# Run the jar file
ENTRYPOINT ["java", "-jar", "/app/app.jar", "--server.port=8082"]



