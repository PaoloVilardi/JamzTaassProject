FROM openjdk:11
LABEL maintainer="PaoloVilardi"
ADD target/GateWay-0.0.1-SNAPSHOT.jar springboot-docker-gateway.jar
ENTRYPOINT ["java", "-jar", "springboot-docker-gateway.jar"]