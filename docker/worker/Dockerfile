FROM maven:3.9-eclipse-temurin-21 AS build

WORKDIR /app

COPY pom.xml .
COPY common/pom.xml common/pom.xml
COPY api/pom.xml api/pom.xml
COPY worker/pom.xml worker/pom.xml

COPY common/src common/src
COPY worker/src worker/src

RUN mvn clean package -pl worker -am -DskipTests

FROM eclipse-temurin:21-jre

WORKDIR /app

COPY --from=build /app/worker/target/*.jar app.jar

ENTRYPOINT ["java", "-jar", "app.jar"]