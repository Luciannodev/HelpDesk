FROM openjdk:11
ADD .build/libs/HelpDesk-0.0.1-SNAPSHOT.jar HelpDesk-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java" ,"-jar", "HelpDesk-0.0.1-SNAPSHOT.jar" ]