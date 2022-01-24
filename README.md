# kubernetes

### Prototype Project
* Spring boot application
* Liquibase application
* Infrastructure local and minikube

### Setup
* docker desktop 
* minikube
* helm
* maven

### MiniKube
##### minikube installation
```
https://github.com/kubernetes/minikube
```

##### Start minikube on docker
```
minikube start
minikube dashboard
```

### Go to k8-infrastructure module
##### Run docker-minikube script
```
./docker-minikube.sh <namespace>
```

### Helm
##### Run following commands to enable chartmuseum and docker registry
* Skip this step if you already run docker-minikube.sh from k8-infrastructure
* Navigate to helm/registry folder

```
helm repo add stable https://charts.helm.sh/stable

helm repo up
helm dep up
helm install <namespace> . --namespace <namespace>
helm install psql-<namespace> cetic/postgresql -n <namespace>

helm repo add <namespace> http://localhost:32702
```

##### Deploy using helm
```
helm install psql-callisto cetic/postgresql -n callisto
```

##### Useful commands
```
helm repo list
helm repo remove <namespace>
helm list -n <namespace>
helm get manifest <release_name> -n <namespace>
helm history <release_name> -n <namespace>
helm rollback <release_name> <revision> -n <namespace>
helm delete <release_name> -n <namespace>
helm search hub postgresql
```
