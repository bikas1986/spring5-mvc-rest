FROM openjdk:8-jdk-alpine
EXPOSE 8080
ADD target/spring5-mvc-rest-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["sh", "-c", "java -jar /app.jar"]