FROM openjdk:11-jdk-slim

EXPOSE 8081

# Directly reference the JAR file without using an argument
ADD target/gestion-station-ski-1.0.0.jar gestion-station-ski.jar

ENTRYPOINT ["java", "-jar", "/gestion-station-ski.jar"]
