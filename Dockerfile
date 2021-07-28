FROM openjdk:8
VOLUME /tmp
ADD ./target/products-0.0.1-SNAPSHOT.jar products-service.jar
ENTRYPOINT ["java", "-jar", "/products-service.jar"]