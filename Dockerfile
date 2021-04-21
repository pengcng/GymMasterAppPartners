FROM openjdk:8
ADD target/gymmasterapppartners-0.0.1-SNAPSHOT.jar partners.jar
EXPOSE 5102
ENTRYPOINT ["java", "-jar", "-Dspring.profiles.active=dev", "partners.jar"]
