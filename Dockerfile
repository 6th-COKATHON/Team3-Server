FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/hackathon-project-0.0.1-SNAPSHOT.jar /app/hackathon-server.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=dev", "hackathon-server.jar"]