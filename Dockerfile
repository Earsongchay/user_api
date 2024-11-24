# Build Stage
FROM maven:3.9.9-amazoncorretto-21-alpine as build

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and project files
COPY pom.xml ./
COPY src/ ./src/

RUN mvn clean package -DskipTests

FROM openjdk:21-slim

WORKDIR /app

EXPOSE 8080

COPY --from=build /app/target/user_api-0.0.1-SNAPSHOT.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
