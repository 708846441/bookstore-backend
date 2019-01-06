FROM openjdk:8-jdk-alpine
ARG JAR_FILE
ADD ${JAR_FILE} bookstore.jar
ENTRYPOINT [ "java", "-jar", "/bookstore.jar"]