# Use official OpenJDK image as the base image for building
FROM openjdk:17-jdk-alpine AS builder

# Set the working directory in the container
WORKDIR /app

# Copy the Gradle wrapper files to the container
COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
# Copy the source code to the container
COPY src ./src

# Build the application using the Gradle wrapper
RUN ./gradlew clean build

# Use a new base image for running the application
FROM openjdk:17-jdk-alpine

# Set the working directory in the container
WORKDIR /app

# Copy the built jar file from the builder stage to the container
COPY --from=builder /app/build/libs/weather-1.0.jar /app/weather-1.0.jar

# Expose the port that the application will run on
EXPOSE 8080

# Run the jar file when the container launches
CMD ["java", "-jar", "weather-1.0.jar"]
