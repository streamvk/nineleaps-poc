FROM openjdk:8
MAINTAINER vivek.rathi@nineleaps.com
ADD target/nineleaps-registry.jar nineleaps-registry.jar
EXPOSE 8761
ENTRYPOINT ["java", "-jar", "nineleaps-registry.jar"]