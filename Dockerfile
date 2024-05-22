# Use a lightweight Java base image for efficiency
FROM openjdk:17-slim-buster AS build

# Update package lists efficiently (optional for frequently updated images)
RUN apt-get update

# Install Maven for building the Java application
RUN apt-get update --no-cache
RUN apt-get install maven -y

# Copy project source code to the build context
COPY . .

# Build the application with Maven (assuming a pom.xml file is present)
WORKDIR /app
RUN mvn clean install

# Switch to a slim runtime image for production
FROM openjdk:17-slim

# Expose the application port
EXPOSE 8080

# Copy the built JAR file from the build stage
COPY --from=build target/barbertime-0.0.1-SNAPSHOT.jar app.jar

# Define the main process as running the JAR
ENTRYPOINT ["java", "-jar", "app.jar"]