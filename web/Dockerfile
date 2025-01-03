# Use a base OpenJDK image
FROM openjdk:17-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file and .env file
COPY web-0.0.2.jar /app/web.jar
COPY .env /app/.env

# Expose the application port (adjust as needed)
EXPOSE 8080

# Command to run the JAR
CMD ["java", "-jar", "/app/web.jar"]
