# Use a base OpenJDK image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file and .env file
COPY . /app

# Expose the application port (adjust as needed)
EXPOSE 8027

# Command to run the JAR
CMD ["java", "-jar", "/app/web.jar"]
