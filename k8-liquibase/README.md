# Spring Boot & Liquibase & PostgreSQL on Kubernetes

Running spring-boot application with Liquibase schema manager.

## Run local PostgreSQL on docker
##### Go to k8-infrastructure and run
```
./docker-postgres.sh <image_name> <port> <db_name> <db_pwd>
```

##### Run application local
* PostgreSQL should be running on docker

```
mvn clean install
mvn spring-boot:run
```

## Build and deploy image to registry
```
./build.sh <version>
```

##### Check Image repository
```
curl http://localhost:32701/v2/_catalog
curl http://localhost:32701/v2/timecard-liquibase/tags/list
```

##### Install chart
```
helm install <pod_name> http://localhost:32702/charts/liquibase-<version>.tgz -n <namespace>
or to upgrade
helm upgrade <pod_name> http://localhost:32702/charts/liquibase-<version>.tgz -n <namespace>
```
