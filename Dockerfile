FROM openjdk:17-oracle
MAINTAINER Sourabh Bhavsar
COPY build/libs/takehome-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]