# Use a base OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file and .env file
COPY data.jar /app/data.jar
COPY .env /app/.env

# Command to run the JAR
CMD ["java", "-jar", "/app/data.jar"]
