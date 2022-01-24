# Spring Boot & PostgreSQL on Kubernetes

Running spring-boot rest application using swagger UI. 
Deployment of PostgreSQL and spring-boot application.

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

## Maven Deploy Image to registry
```
./build.sh
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
kubectl delete pod <service_name> -n <namespace>
kubectl describe pod <pod_name> -n <namespace>
kubectl get deployments -n <namespace>
kubectl get services -n <namespace>
kubectl get pods -n <namespace>
kubectl delete --all pods -n <namespace>
kubectl logs <pod_name> -n <namespace>
kubectl exec -it <pod_name> -n <namespace> -- sh /# printenv
```

##### Create namespace
* Skip this step if you already run docker-minikube.sh from k8-infrastructure

```
kubectl get namespace
kubectl create namespace <namespace>
```

##### Show all charts
```
http://localhost:32702/api/charts
```

##### Install application chart
```
helm install <pod_name> http://localhost:32702/charts/callisto-<version>.tgz -n <namespace>
or to upgrade
helm upgrade <pod_name> http://localhost:32702/charts/callisto-<version>.tgz -n <namespace>
```

## Application Swagger
````
http://localhost:30001/swagger-ui/index.html#/
```

## PostgreSQL
##### Login
```
kubectl run psql-<namespace>-postgresql-client --rm --tty -i --restart='Never' --namespace <namespace> --image postgres --env="PGPASSWORD=postgres" --command -- psql --host psql-<namespace>-postgresql.<namespace>.svc.cluster.local -U postgres -d postgres -p 5432

DNS:
psql-<namespace>-postgresql.<namespace>.svc.cluster.local

Default properties:
postgresql.username	postgresql username	postgres
postgresql.password	postgresql password	postgres
postgresql.database	postgresql database	postgres
postgresql.port		postgresql port		5432

SELECT datname FROM pg_database;
\c postgres
\dt
```

##### Delete
```
helm delete --purge psql-<namespace>
```

##### Deploy from scripts
###### Navigate to src/main/k8/postgres folder

```
kubectl apply -f postgres-configmap.yaml -n <namespace>
kubectl apply -f postgres-storage.yaml -n <namespace>
kubectl get pvc -n <namespace>
kubectl apply -f postgres-deployment.yaml -n <namespace>
kubectl apply -f postgres-service.yaml -n <namespace>
kubectl get all -n <namespace>

kubectl exec -it [pod-name] --  psql -h localhost -U admin --password -p [port] postgresdb
```

###### Delete
```
kubectl delete -f postgres-configmap.yaml -n <namespace>
kubectl delete -f postgres-storage.yaml -n <namespace>
kubectl delete -f postgres-deployment.yaml -n <namespace>
kubectl delete -f postgres-service.yaml -n <namespace>

kubectl get all -n <namespace>
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
https://artifacthub.io/packages/helm/cetic/postgresql
```
