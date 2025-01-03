# Use a base OpenJDK image
FROM openjdk:21-jdk-slim

# Set the working directory
WORKDIR /app
COPY .env /.env

# Copy the JAR file and .env file
COPY data.jar /app/data.jar

# Command to run the JAR
CMD ["java", "-jar", "/app/data.jar"]
