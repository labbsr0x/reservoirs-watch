#Build Environment
#Image name should be reservoir-quarkus-cache for local use
FROM quay.io/quarkus/centos-quarkus-maven:19.0.2 as build
USER root

ADD ./pom.xml /project/pom.xml
RUN mvn dependency:resolve