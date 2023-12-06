FROM gradle:7.4.0-jdk17 AS notifi_builder
WORKDIR /app
COPY / ./
RUN gradle task bootJar

FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y curl
ENV APPLICATION_PORT 8080
ENV CONFIGURACION_URL=
ENV EUREKA_URI=
ENV DB_URL=
ENV DB_USER=
ENV DB_PASS=


WORKDIR /app
COPY --from=notifi_builder /app/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app/app.jar"]
