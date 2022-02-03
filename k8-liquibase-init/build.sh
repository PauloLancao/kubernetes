#!/bin/bash

if [ -z "$1" ] 
then
	echo "Missing application version"
	exit 1
fi
if [ -z "$2" ] 
then
	echo "Missing namespace"
	exit 1
fi

sed -i 's/^[#]*\s*appVersion:.*/appVersion: "'$1'"/g' 'src/main/helm/application/Chart.yaml'
sed -i 's/^[#]*\s*ENV NAMESPACE=.*/ENV NAMESPACE='$2'/g' 'Dockerfile'

mvn versions:set -DgenerateBackupPoms=false -DnewVersion=$1
mvn clean deploy