# Use the OpenJDK 11 slim image as the base image
FROM openjdk:11-jdk-slim

# Expose port 8081 (the port your application will run on)
EXPOSE 8081

# Define the JAR version as an argument (this will be passed during build)
ARG JAR_VERSION

# Add the built JAR file to the container (make sure it exists in the target directory)
ADD target/gestion-station-ski-${JAR_VERSION}.jar gestion-station-ski.jar

# Set the entry point to run the JAR file when the container starts
ENTRYPOINT ["java", "-jar", "/gestion-station-ski.jar"]
