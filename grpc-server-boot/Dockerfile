FROM maven:3.9.4-amazoncorretto-21 AS build
COPY pom.xml /home/app/pom.xml
RUN mvn -f /home/app/pom.xml dependency:go-offline
COPY src /home/app/src
COPY checkstyle.xml /home/app
RUN mvn -f /home/app/pom.xml clean package -Dmaven.test.skip

FROM amazoncorretto:21
RUN mkdir /app
WORKDIR /app
COPY --from=build /home/app/target/*.jar /app/app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]