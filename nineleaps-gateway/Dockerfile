FROM openjdk:8
MAINTAINER vivek.rathi@nineleaps.com
ADD target/nineleaps-gateway.jar nineleaps-gateway.jar
EXPOSE 8765
ENTRYPOINT ["java", "-jar", "nineleaps-gateway.jar"]