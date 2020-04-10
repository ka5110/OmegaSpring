## Start with a base image containing Java runtime (mine java 8)
#FROM adoptopenjdk/openjdk11:alpine-jre
##FROM openjdk:8u212-jdk-slim##########
#
##Author
#MAINTAINER omega.com
#
## The application's jar file (when packaged)
#ARG JAR_FILE=target/*.jar
#
## Make port 8080 available to the world outside this container
#EXPOSE 5000
#
## Add a volume pointing to /tmp
#VOLUME /tmp
#
## Add the application's jar to the container
##ADD ${JAR_FILE} codestatebkend.jar#############
#COPY ${JAR_FILE} app.jar
#
## Run the jar file
#ENTRYPOINT ["java","-jar","/app.jar"]



FROM openjdk:11.0-jre-stretch
WORKDIR /usr/share/omega-k8s
ARG appDir=/usr/share/omega-k8s
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib ${appDir}/lib
COPY ${DEPENDENCY}/META-INF ${appDir}/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes ${appDir}
EXPOSE 5000
ENTRYPOINT ["java","-cp","com/project/omega/*:lib/*:.","com.project.omega.Application"]

    # we use the maven-dependency-plugin to unpack the fat jar under target/dependency
    # then we copy /BOOT-INF/lib, /BOOT-INF/classes and /META-INF to the base image
    # then in the ENTRYPOINT we run the main class by specifying the classpath
