FROM openjdk:17-slim-buster AS build
RUN apt-get install -f && apt-get install maven -y
COPY . .
RUN mvn clean install
FROM openjdk:17-slim
EXPOSE 8080
COPY --from=build target/barbertime-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
