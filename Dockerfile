FROM eclipse-temurin:17-jdk

WORKDIR /app

COPY target/loanmanagement-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 9095

ENTRYPOINT ["java", "-jar", "app.jar"]