FROM openjdk:17
COPY target/ds_chat-0.0.1-SNAPSHOT.jar ds_chat-0.0.1-SNAPSHOT.jar
EXPOSE 8085
CMD ["java", "-jar", "ds_chat-0.0.1-SNAPSHOT.jar"]