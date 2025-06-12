FROM gradle:8.12-jdk21 AS build

ENV TESTCONTAINERS_DB=${TESTCONTAINERS_DB}
ENV TESTCONTAINERS_USERNAME=${TESTCONTAINERS_USERNAME}
ENV TESTCONTAINERS_PASSWORD=${TESTCONTAINERS_PASSWORD}

WORKDIR /workspace/app

COPY ../ ./
#
#RUN (cd ww-starter-jwt; gradle clean publishToMavenLocal --stacktrace --parallel --console=plain)
#
#RUN (cd ww-starter-minio; gradle clean publishToMavenLocal --stacktrace --parallel --console=plain)

RUN gradle :ww-user-service:clean :ww-user-service:assemble --stacktrace --parallel --console=plain

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /workspace/app/ww-user-service/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]