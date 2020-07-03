FROM openjdk:8
ENV JAVA_OPTS=""
MAINTAINER vivek.rathi@nineleaps.com
ADD target/registry.jar registry.jar
EXPOSE 8761
#ENTRYPOINT ["java", "-jar", "registry.jar"]
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar registry.jar"]

