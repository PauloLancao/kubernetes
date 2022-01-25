FROM liquibase/liquibase
ADD src/main/resources/db/changelog /db/changelog
ENV URL=jdbc:postgresql://psql-callisto-postgresql.callisto.svc.cluster.local:5432/postgres?createDatabaseIfNotExist=true&autoReconnect=true&useSSL=false&allowPublicKeyRetrieval=true
ENV USERNAME=postgres
ENV PASSWORD=postgres
CMD ["sh", "-c", "docker-entrypoint.sh --url=${URL} --username=${USERNAME} --password=${PASSWORD} --classpath=/db/changelog --changeLogFile=db.changelog-master.yaml update"]
