FROM java:8

ADD bookstore-1.0.0-SNAPSHOT.jar bookstore.jar

ENTRYPOINT ["java", "-Duser.timezone=GMT+8", "-jar", "/bookstore.jar"]