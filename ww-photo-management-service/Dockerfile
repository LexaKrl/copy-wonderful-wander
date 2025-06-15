FROM eclipse-temurin:21-jre-alpine

WORKDIR /workspace/app

COPY ../ww-photo-management-service/build/libs/ww-photo-management-service.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]