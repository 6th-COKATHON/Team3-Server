FROM eclipse-temurin:17-jdk
WORKDIR /app
COPY build/libs/app.jar /app/hackathon-server.jar
CMD ["java", "-Duser.timezone=Asia/Seoul", "-jar", "-Dspring.profiles.active=prod", "hackathon-server.jar"]