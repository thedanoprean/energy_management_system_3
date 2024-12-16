FROM maven:3.6.3-openjdk-17-slim
WORKDIR /app
COPY target/usersproject-0.0.1-SNAPSHOT.jar .
EXPOSE 8088
ENTRYPOINT ["java","-jar","usersproject-0.0.1-SNAPSHOT.jar"]

