FROM eclipse-temurin:21-jre-alpine

WORKDIR /workspace/app

COPY ../ww-post-service/build/libs/ww-post-service.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]