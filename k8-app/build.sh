#!/bin/bash
sed -i 's/^[#]*\s*appVersion:.*/appVersion: "'$1'"/g' 'src/main/helm/application/Chart.yaml'
mvn versions:set -DnewVersion=$1
mvn clean deploy