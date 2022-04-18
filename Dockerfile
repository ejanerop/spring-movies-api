FROM maven:3.8.2-jdk-8 as builder
COPY . .
RUN mvn package -Dmaven.test.skip

FROM openjdk:8-jdk-alpine
WORKDIR /spring
ARG JAR_FILE=target/*.jar
COPY --from=builder ${JAR_FILE} app.jar
RUN addgroup -S spring && adduser -S spring -G spring
USER spring:spring
EXPOSE 8080
ENTRYPOINT ["java","-jar","/spring/app.jar"]
