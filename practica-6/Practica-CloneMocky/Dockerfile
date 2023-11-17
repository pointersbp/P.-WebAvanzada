# docker build -t donnie998/dockercompose:2.0
FROM gradle:7.4.0-jdk17 as builder
WORKDIR /app
COPY / ./
RUN gradle bootJar

FROM openjdk:17-jdk-slim
ENV APPLICATION_PORT 8080
ENV APP_DB_URL=
ENV APP_DB_DRIVER_CLASSNAME=
ENV APP_DB_PLATFORM=
ENV APP_DB_USERNAME=
ENV APP_DB_PASSWORD=
WORKDIR /app
EXPOSE ${APPLICATION_PORT}/tcp
#COPY --from=builder /app/build/libs/*.jar app.jar
COPY build/libs/Practica-CloneMocky-0.0.1-SNAPSHOT.jar ./app.jar
#build/libs/Practica-CloneMocky-0.0.1-SNAPSHOT.jar
#COPY --from=builder $JAR_FILE app.jar
#COPY --from=builder /build/libs/*.jar ./app.jar
ENTRYPOINT ["java", "-jar", "./app.jar"]
#COPY running_script.sh run.sh
#ENTRYPOINT ["sh","./run.sh"]
