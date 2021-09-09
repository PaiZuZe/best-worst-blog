FROM maven:3.8.2-openjdk-16

EXPOSE 8080

RUN /bin/sh -c "microdnf -y install nc"

COPY ./target/*.jar app.jar

ENTRYPOINT [ "java", "-jar", "/app.jar" ]