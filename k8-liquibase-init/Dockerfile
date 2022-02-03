FROM liquibase/liquibase
ADD src/main/resources/db/changelog /db/changelog
ENV NAMESPACE=prototype
ENV URL=jdbc:postgresql://psql-${NAMESPACE}-postgresql.${NAMESPACE}.svc.cluster.local:5432/postgres?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false
ENV USERNAME=postgres
ENV PASSWORD=postgres
CMD ["sh", "-c", "docker-entrypoint.sh --url=${URL} --username=${USERNAME} --password=${PASSWORD} --classpath=/db/changelog --changeLogFile=db.changelog-master.yaml update"]
