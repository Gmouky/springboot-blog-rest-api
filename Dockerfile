FROM eclipse-temurin:17

LABEL mentainer="mouky@mouky.com"

WORKDIR /app

COPY target/springboot-blog-rest-api-0.0.1-SNAPSHOT.jar /app/spring-boot-blog-rest-api.jar

ENTRYPOINT ["java", "-jar", "spring-boot-blog-rest-api.jar"]