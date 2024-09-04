FROM maven:3.8.6-amazoncorretto-17 AS build
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM amazoncorretto:17-alpine-jdk
LABEL author="Dyastrophism"
COPY --from=build /app/target/*.jar /app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]
