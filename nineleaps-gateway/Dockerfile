FROM openjdk:8
MAINTAINER vivek.rathi@nineleaps.com
ENV JAVA_OPTS=""
ADD target/gateway.jar gateway.jar
EXPOSE 8765
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar gateway.jar"]
