FROM openjdk:17-jdk-alpine
EXPOSE 8089
ADD gestion-station-ski-1.0.jar gestion-station-ski-1.0.jar
ENTRYPOINT ["java","-jar","/gestion-station-ski-1.0.jar"]