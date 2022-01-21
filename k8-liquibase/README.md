# Spring Boot & Liquibase & PostgreSQL on Kubernetes

Running spring-boot application with Liquibase schema manager.

## Build and deploy image to registry
```
./build.sh <version>
```

## Running local PostgreSQL on docker for testing
```
docker run --name foo-psql \
    -p 5450:5432 \
    -e POSTGRES_DB=postgres \
    -e POSTGRES_PASSWORD=postgres \
    -d postgres
```

## Maven Build and Push Image
##### Steps to run locally
```
mvn clean install
mvn spring-boot:run
```

##### Push Image
```
mvn clean deploy
```

##### Check Image repository
```
curl http://localhost:32701/v2/_catalog
curl http://localhost:32701/v2/timecard-liquibase/tags/list
```

##### Install chart
```
helm install liquibase-service http://localhost:32702/charts/liquibase-1.0.0.tgz -n callisto
or to upgrade
helm upgrade liquibase-service http://localhost:32702/charts/liquibase-1.0.0.tgz -n callisto
```
