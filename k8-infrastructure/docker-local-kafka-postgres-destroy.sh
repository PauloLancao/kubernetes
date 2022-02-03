#!/bin/bash

yellow=`tput setaf 3`

echo ""
echo "${yellow}Destroy Local PostgreSQL"
docker rm -f postgres-local

echo ""
echo "${yellow}Destroy Local Kafka"
docker rm -f $(docker ps -a | grep "kafka-local")