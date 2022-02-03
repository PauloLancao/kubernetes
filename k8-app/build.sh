#!/bin/bash

if [ -z "$1" ] 
then
	echo "Missing application version"
	exit 1
fi
if [ -z "$2" ] 
then
	echo "Missing liquibase version"
	exit 1
fi

sed -i 's/^[#]*\s*appVersion:.*/appVersion: "'$1'"/g' 'src/main/helm/application/Chart.yaml'
sed -i 's/^[#]*\s*liquibaseVersion:.*/liquibaseVersion: '$2'/g' 'src/main/helm/application/values.yaml'

mvn versions:set -DgenerateBackupPoms=false -DnewVersion=$1
mvn clean deploy