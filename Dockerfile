FROM maven:3.6.3-jdk-8
MAINTAINER Megha

COPY . /usr/src/app/
WORKDIR /usr/src/app/

RUN mvn -f /usr/src/app/pom.xml clean package
RUN mv target/CarTracker-1.0.0.jar app.jar
ENTRYPOINT java -jar app.jar