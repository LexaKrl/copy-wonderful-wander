FROM eclipse-temurin:21-jre-alpine

WORKDIR /workspace/app

COPY ../ww-user-service/build/libs/ww-user-service.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]