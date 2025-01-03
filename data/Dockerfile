# Use a base OpenJDK image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app

# Copy the JAR file and .env file
COPY data.jar /app/data.jar

# Command to run the JAR
CMD ["java", "-jar", "/app/data.jar"]
