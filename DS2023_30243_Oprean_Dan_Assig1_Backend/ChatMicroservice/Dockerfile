FROM maven:3.6.3-openjdk-17-slim
WORKDIR /app
COPY target/ChatMicroservice-0.0.1-SNAPSHOT.jar .
EXPOSE 8090
ENTRYPOINT ["java","-jar","ChatMicroservice-0.0.1-SNAPSHOT.jar"]

