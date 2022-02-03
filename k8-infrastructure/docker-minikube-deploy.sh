#!/bin/bash

reset=`tput sgr0`
red=`tput setaf 1`
green=`tput setaf 2`
yellow=`tput setaf 3`
blue=`tput setaf 4`

output=$(kubectl config get-contexts)
echo "${yellow}Minikube contexts"
echo "${green}${output}"
echo ""

echo "${yellow}Minikube switch context to docker-desktop"
output=$(kubectl config use-context docker-desktop)
echo "${green}${output}"
echo ""

echo "${yellow}Minikube namespaces"
output=$(kubectl get namespace)
echo "${green}${output}"
echo ""
echo "${yellow}Minikube create namespace"
if [ -z "$1" ] 
then
	echo "${red}Missing namespace name"
	exit 1
fi

output=$(kubectl create namespace $1)
echo "${green}${output}"

if [[ $output == "" ]]; then
	read -p "${red}Namespace already exists, do you wish to continue? [YyNn]" -n 1 -r
	echo    # (optional) move to a new line
	if [[ ! $REPLY =~ ^[Yy]$ ]]
	then
		echo "Exiting..."
	    exit 1
	fi
fi

echo ""
echo "${yellow}Install registry"
cd helm/registry
helm repo add stable https://charts.helm.sh/stable
helm repo add cetic https://cetic.github.io/helm-charts
helm repo add bitnami https://charts.bitnami.com/bitnami

echo ""
echo "${yellow}Update registry"
helm repo up
helm dep up
helm install $1 . --namespace $1

echo ""
echo "${yellow}Register registry"
helm repo add $1 http://localhost:32702

echo ""
echo "${green}Show charts"
echo "http://localhost:32702/api/charts"

echo ""
echo "${yellow}Install PostgreSQL"
helm install psql-$1 cetic/postgresql -n $1

echo ""
echo "${yellow}Install Kafka"
helm install kafka-$1 bitnami/kafka -n $1
