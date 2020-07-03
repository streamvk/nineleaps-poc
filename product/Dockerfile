FROM openjdk:8
ENV JAVA_OPTS=""
MAINTAINER vivek.rathi@nineleaps.com
ADD target/product.jar product.jar
EXPOSE 1020
#ENTRYPOINT ["java", "-jar", "product.jar"]
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar product.jar"]