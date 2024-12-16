FROM maven:3.6.3-openjdk-17-slim
WORKDIR /app
COPY target/devicesproject-0.0.1-SNAPSHOT.jar .
EXPOSE 8087
ENTRYPOINT ["java","-jar","devicesproject-0.0.1-SNAPSHOT.jar"]

