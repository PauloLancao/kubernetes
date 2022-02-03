#!/bin/bash

yellow=`tput setaf 3`

echo "${yellow}Start Local PostgreSQL"
./postgres/docker/docker-postgres-local.sh postgres-local 5450 postgres postgres postgres

echo ""
echo "${yellow}Start Local Kafka"
docker-compose -f kafka/docker/docker-compose.yaml -p kafka-local up -d