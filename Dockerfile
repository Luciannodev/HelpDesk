FROM openjdk:11

COPY /kaniko/buildcontext/build/libs/HelpDesk-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","app.jar"]