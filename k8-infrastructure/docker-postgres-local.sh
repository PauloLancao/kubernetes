#!/bin/bash

if [ -z "$1" ] 
then
	echo "Missing image name"
	exit 1
fi
if [ -z "$2" ] 
then
	echo "Missing db port"
	exit 1
fi
if [ -z "$3" ] 
then
	echo "Missing db name"
	exit 1
fi
if [ -z "$4" ] 
then
	echo "Missing db pwd"
	exit 1
fi

echo "Start docker postgres image"

docker run --name $1 \
    -p $2:5432 \
    -e POSTGRES_DB=$3 \
    -e POSTGRES_PASSWORD=$4 \
    -d postgres
    
echo "End docker postgres image"