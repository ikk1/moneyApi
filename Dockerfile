FROM maven:3.8.4-openjdk-17-slim AS build
WORKDIR /app
COPY . /app
RUN mvn -B -DskipTests clean package

FROM openjdk:17-oracle
WORKDIR /app
COPY --from=build /app/target/money-api-0.0.1-SNAPSHOT.jar /app/money-api.jar
EXPOSE 8080
CMD ["java", "-jar", "money-api.jar"]