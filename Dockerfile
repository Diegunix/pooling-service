FROM java:8-jdk-alpine

COPY ./target/Meep.jar /usr/app/

WORKDIR /usr/app

RUN sh -c 'touch Meep.jar'

ENTRYPOINT ["java","-jar","Meep.jar"]  