FROM openjdk:8
ENV JAVA_OPTS=""
MAINTAINER vivek.rathi@nineleaps.com
ADD target/order.jar order.jar
EXPOSE 1030
#ENTRYPOINT ["java", "-jar", "order.jar"]
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar order.jar"]