FROM openjdk:17-oracle

WORKDIR /app

COPY target/money-api-0.0.1-SNAPSHOT.jar /app/money-api.jar

EXPOSE 8080

CMD ["java", "-jar", "money-api.jar"]