#
FROM openjdk:17 as build
WORKDIR /app
COPY . .
RUN ./gradlew build

FROM openjdk:17-jre
ENV APPLICATION_PORT 8080
ENV APP_DB_URL=
ENV APP_DB_DRIVER_CLASSNAME=
ENV APP_DB_PLATFORM=
ENV APP_DB_USERNAME=
ENV APP_DB_PASSWORD=
WORKDIR /app
EXPOSE ${APPLICATION_PORT}/tcp
COPY --from=builder /app/build/libs/*.jar app.jar

COPY running_script.sh run.sh
ENTRYPOINT ["sh","./run.sh"]

