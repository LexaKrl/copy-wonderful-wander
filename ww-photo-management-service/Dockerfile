FROM gradle:8.12-jdk21 AS build

WORKDIR /workspace/app

COPY ../ ./

RUN (cd ww-starter-jwt; gradle clean publishToMavenLocal --stacktrace --parallel --console=plain)

RUN (cd ww-starter-minio; gradle clean publishToMavenLocal --stacktrace --parallel --console=plain)

RUN gradle  :ww-photo-management-service:--refresh-dependencies  \
    :ww-photo-management-service:clean  \
    :ww-photo-management-service:assemble --stacktrace --parallel --console=plain

FROM eclipse-temurin:21-jre-alpine

COPY --from=build /workspace/app/ww-photo-management-service/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]