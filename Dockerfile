FROM openjdk:11-jdk-slim
EXPOSE 8081
ARG JAR_VERSION
ADD target/gestion-station-ski-${JAR_VERSION}.jar gestion-station-ski.jar
ENTRYPOINT ["java", "-jar", "/gestion-station-ski.jar"]
