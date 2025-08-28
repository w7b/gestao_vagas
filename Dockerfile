FROM ubuntu:latest AS build
LABEL authors="Gabriel"

RUN apt-get update
RUN apt-get install openjdk-17-jdk -y
COPY . .

RUN apt-get install maven -y
RUN mvn clean install

FROM openjdk:17-jdk-slim
EXPOSE 8080

COPY --from=build /target/gestao-vagas-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT [ "java", "-XX:-UseContainerSupport", "-jar", "app.jar" ]