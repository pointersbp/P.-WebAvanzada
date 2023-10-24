FROM gradle:7.4.0-jdk17 AS builder
WORKDIR /app
COPY / ./
RUN gradle task bootJar

FROM openjdk:17-jdk-slim
ENV APPLICATION_PORT 8080
ENV BROKER_CONNECTION localhost:61616
WORKDIR /app
EXPOSE ${APPLICATION_PORT}/tcp
COPY --from=builder /app/build/libs/*.jar app.jar
COPY running_script.sh run.sh
ENTRYPOINT ["sh","./run.sh"]