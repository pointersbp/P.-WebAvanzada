FROM gradle:7.4.0-jdk17 AS config_builder
WORKDIR /app
COPY / ./
RUN gradle task bootJar

FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y curl
ENV APPLICATION_PORT 8080


WORKDIR /app
COPY --from=config_builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
