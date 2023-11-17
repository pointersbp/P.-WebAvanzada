
# docker build -t donnie998/balanceadores:latest .
FROM  gradle:7.4.0-jdk17  AS builder
WORKDIR /app
COPY / ./
RUN gradle task bootJar

FROM openjdk:17-jdk-slim
RUN apt-get update && apt-get install -y curl
# Configuración de la aplicación
ENV APPLICATION_PORT 8080
ENV APP_DB_URL=
ENV APP_DB_DRIVER_CLASSNAME=
ENV APP_DB_PLATFORM=
ENV APP_DB_USERNAME=
ENV APP_DB_PASSWORD=
ENV REDIS_HOST=
ENV REDIS_PASS=
ENV REDIS_PORT 6379
WORKDIR /app
EXPOSE ${APPLICATION_PORT}/tcp
COPY --from=builder /app/build/libs/*.jar app.jar

COPY running_script.sh run.sh
ENTRYPOINT ["sh","./run.sh"]

