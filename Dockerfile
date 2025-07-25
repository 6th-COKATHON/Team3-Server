FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/*.jar /app/hackathon-server.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=dev", "hackathon-server.jar"]