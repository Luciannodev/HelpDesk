FROM openjdk:11
COPY build/libs/HelpDesk-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","app.jar","--port","8080:8080"]
