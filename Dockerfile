FROM openjdk:8-jdk
ADD ./target/*.jar /app.jar
CMD ["java", "-jar", "-Dspring.profiles.active=prod", "/app.jar"]
