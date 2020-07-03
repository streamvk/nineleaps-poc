FROM openjdk:8
ENV JAVA_OPTS=""
MAINTAINER vivek.rathi@nineleaps.com
ADD target/supplier.jar supplier.jar
EXPOSE 1010
#ENTRYPOINT ["java", "-jar", "supplier.jar"]
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar supplier.jar"]