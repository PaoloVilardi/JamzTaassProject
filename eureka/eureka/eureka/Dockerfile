FROM openjdk:11
LABEL maintainer="PaoloVilardi"
ADD target/eureka-0.0.1-SNAPSHOT.jar springboot-docker-eureka.jar
ENTRYPOINT ["java", "-jar", "springboot-docker-eureka.jar"]