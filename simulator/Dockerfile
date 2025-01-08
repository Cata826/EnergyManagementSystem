FROM openjdk:19-jdk-alpine

# Set working directory in the container
WORKDIR /app

# Copy the JAR file into the container
COPY target/simulator-1.0-SNAPSHOT.jar /app/simulator.jar

# Copy configuration file into the container

COPY configuration.txt /app/configuration.txt
COPY sensor.csv /app/sensor.csv

# Command to run the JAR file
CMD ["java", "-jar", "/app/simulator.jar"]

