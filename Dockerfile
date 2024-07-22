FROM maven:latest AS maven
WORKDIR /app
COPY pom.xml .
COPY src ./src
RUN mvn clean package -DskipTests

FROM openjdk:latest AS app
WORKDIR /app
ARG JAR_FILE=/app/target/*.jar
COPY --from=maven /app/target/*.jar shoppingManager.jar
ENTRYPOINT ["java", "-jar", "shoppingManager.jar"]