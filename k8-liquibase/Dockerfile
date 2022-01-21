FROM openjdk:17-oracle
ARG JAR_FILE
ENV SPRING_PROFILE=dev
ADD ${JAR_FILE} liquibase.jar
ENTRYPOINT ["java","-jar","/liquibase.jar","--spring.config.location=classpath:/application-${SPRING_PROFILE}.properties"]
EXPOSE 8080