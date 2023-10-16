
FROM openjdk:17 as build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:17-jre
ENV APPLICATION_PORT 1234
VOLUME /tmp
ARG JAR_FILE=build/libs/*.jar
EXPOSE $APPLICATION_PORT:8080
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]