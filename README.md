# kubernetes

### Prototype Project
* Spring boot application
* Spring boot Liquibase
* Liquibase K8 Init container
* Infrastructure local and minikube

### Setup
* docker desktop
* minikube
* helm
* maven
* kubectl
* docker registry (running on docker)
* chart museum (running on docker)

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
##### Deploy k8 stack
```
./docker-minikube-deploy.sh <namespace>
```

##### Destroy k8 deployed stack
```
./docker-minikube-destroy.sh <namespace>
```

##### Deploy local stack
```
./docker-local-kafka-postgres-deploy.sh
```

##### Destroy local deployed stack
```
./docker-local-kafka-postgres-destroy.sh
```

### Helm
##### Run following commands to enable chartmuseum and docker registry
* Skip this step if you already run docker-minikube-deploy.sh from k8-infrastructure
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
helm install psql-<namespace> cetic/postgresql -n <namespace>
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

## Spring boot application + Liquibase Init container

<p>"timecard" is a spring boot project, integrated with Swagger (OpenAPI), exposing a "Foo" and "Moo" Service.</p>
<p>The project is configured to be released in conjunction with "liquibase-init" project.</p>
<p>Helm is used as package manager and it's configured to deploy "liquibase-init" image and after the web application.</p>
<p>There is also a k8 folder which can be used to deploy the web application using kubectl, bare in mind that will depend on how the infrastructure was created.</p>
<p>Helm files located <b>src->main->helm->application</b></p>

<p>Use case</p>
<p>Add liquibase script to project "liquibase-init"</p>
<p>Build and push new image to chartmuseum repository, using build.sh <app_version> script</p>
<p>"timecard" project build and push a new image to chartmuseum repository, using build.sh <app_version> <liquibase_version> script</p>
<p>Using helm, install the chart with the following command</p>
<p>helm install <pod_name> http://localhost:32702/charts/callisto-<app_version>.tgz -n <namespace></p>

## Liquibase standalone spring application

<p>Example on how to setup a spring boot application with integrated Liquibase.</p>
<p>Creates a K8 pod with a running process.</p>
