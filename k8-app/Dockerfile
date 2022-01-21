FROM openjdk:17-oracle
ARG JAR_FILE
ENV SPRING_PROFILE=dev
ADD ${JAR_FILE} app.jar
ENTRYPOINT ["java","-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=*:8000","-jar","/app.jar","--spring.config.location=classpath:/application-${SPRING_PROFILE}.properties"]
EXPOSE 8080
EXPOSE 8000