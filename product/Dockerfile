FROM openjdk:8
MAINTAINER vivek.rathi@nineleaps.com
ADD target/product.jar product.jar
EXPOSE 1020
ENTRYPOINT ["java", "-jar", "product.jar"]