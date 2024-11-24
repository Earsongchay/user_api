# Build Stage
FROM maven:3.9.1-openjdk-21-slim as build

# Set the working directory
WORKDIR /app

# Copy the Maven wrapper and project files
COPY pom.xml ./
COPY src/ ./src/

# Build the application
RUN mvn clean package -DskipTests

# Production Stage
FROM openjdk:21-slim

# Set the working directory
WORKDIR /app

# Expose the port the app runs on
EXPOSE 8080

# Copy the built jar file from the build stage
COPY --from=build /app/target/user_api-0.0.1-SNAPSHOT.jar app.jar

# Command to run the application
ENTRYPOINT ["java", "-jar", "app.jar"]
