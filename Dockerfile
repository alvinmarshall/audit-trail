
FROM gradle:jdk21-jammy AS build
WORKDIR /app
COPY ./src /app/src
COPY *.gradle  /app
RUN gradle build -x test --no-daemon

FROM eclipse-temurin:21.0.2_13-jre-jammy

COPY --from=build /app/build/libs/*.jar /app.jar

ENTRYPOINT ["java","-jar","/app.jar"]
