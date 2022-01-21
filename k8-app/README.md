# Spring Boot & PostgreSQL on Kubernetes

Running spring-boot rest application using swagger UI. 
Deployment of PostgreSQL and spring-boot application.

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

## MiniKube
##### minikube installation
```
https://github.com/kubernetes/minikube
```

##### Start minikube on docker
```
minikube start
minikube dashboard
```

### Check which context minikube is running
##### Switch to docker-desktop.
```
kubectl config get-contexts
kubectl config use-context docker-desktop
```

##### Create namespace
```
kubectl get namespace
kubectl create namespace callisto
```

## Helm
##### Run following commands to enable chartmuseum and docker registry
Navigate to src/main/helm/registry folder

```
helm repo add stable https://charts.helm.sh/stable

helm repo up
helm dep up
helm install callisto . --namespace callisto

helm repo add callisto http://localhost:32702
```

##### Useful commands
```
helm list -aq
helm delete timecard
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
curl http://localhost:32701/v2/timecard/tags/list
```

## K8 Deployment

##### Useful commands
```
kubectl cluster-info
kubectl delete pod <service_name> -n callisto
kubectl describe pod <pod_name> -n callisto
kubectl get deployments -n callisto
kubectl get services -n callisto
kubectl get pods -n callisto
kubectl delete --all pods -n callisto
kubectl logs <pod_name> -n callisto

helm list -n callisto
helm get manifest <release_name> -n callisto
helm history <release_name> -n callisto
helm rollback <release_name> <revision> -n callisto
helm search hub postgresql
```

##### Show all charts
```
http://localhost:32702/api/charts
```

##### Install chart
```
helm install foo-service http://localhost:32702/charts/callisto-1.0.0.tgz -n callisto
or to upgrade
helm upgrade foo-service http://localhost:32702/charts/callisto-1.0.0.tgz -n callisto
```

## Application Swagger
````
http://localhost:30001/swagger-ui/index.html#/
```

## PostgreSQL

##### Using bitnami helm chart repository
```
helm repo add bitnami https://charts.bitnami.com/bitnami
helm repo update
```

##### Deploy using helm
```
helm install psql-callisto cetic/postgresql -n callisto

kubectl run psql-callisto-postgresql-client --rm --tty -i --restart='Never' --namespace callisto --image postgres --env="PGPASSWORD=postgres" --command -- psql --host psql-callisto-postgresql.callisto.svc.cluster.local -U postgres -d postgres -p 5432

DNS:
psql-callisto-postgresql.callisto.svc.cluster.local

Default properties:
postgresql.username	postgresql username	postgres
postgresql.password	postgresql password	postgres
postgresql.database	postgresql database	postgres
postgresql.port		postgresql port		5432

SELECT datname FROM pg_database;
\c postgres
\dt

helm delete --purge psql-callisto
```

##### Deploy configuration from scripts
Navigate to src/main/k8/postgres folder

```
kubectl apply -f postgres-configmap.yaml -n callisto
kubectl apply -f postgres-storage.yaml -n callisto
kubectl get pvc -n callisto
kubectl apply -f postgres-deployment.yaml -n callisto
kubectl apply -f postgres-service.yaml -n callisto
kubectl get all -n callisto

kubectl exec -it [pod-name] --  psql -h localhost -U admin --password -p [port] postgresdb
```

### Delete PostgreSQL
```
kubectl delete -f postgres-configmap.yaml -n callisto
kubectl delete -f postgres-storage.yaml -n callisto
kubectl delete -f postgres-deployment.yaml -n callisto
kubectl delete -f postgres-service.yaml -n callisto

kubectl get all -n callisto
```

## Used resources
```
https://blog.nebrass.fr/playing-with-spring-boot-on-kubernetes/
https://www.sumologic.com/blog/kubernetes-deploy-postgres/
https://severalnines.com/database-blog/using-kubernetes-deploy-postgresql
https://www.baeldung.com/spring-rest-openapi-documentation
https://github.com/eugenp/tutorials/tree/master/spring-boot-modules/spring-boot-springdoc
https://minikube.sigs.k8s.io/docs/handbook/mount/
https://www.baeldung.com/spring-boot-minikube
https://medium.com/@stickelm/tutorial-create-build-and-deploy-a-spring-boot-application-in-a-local-kubernetes-cluster-35ceaf7213bf
https://github.com/mstickel/helm-kubernetes-local
https://docs.bitnami.com/kubernetes/faq/troubleshooting/troubleshooting-helm-chart-issues/
https://github.com/cetic/helm-postgresql
https://gitlab.com/tofik.mikailov/liquibase-init-container/-/blob/main/Dockerfile
```
