FROM eclipse-temurin:21-jre-alpine

WORKDIR /workspace/app

COPY ../ww-api-gateway/build/libs/ww-api-gateway.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]