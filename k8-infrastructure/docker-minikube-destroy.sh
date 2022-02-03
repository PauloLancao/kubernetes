#!/bin/bash

if [ -z "$1" ] 
then
	echo "Missing namespace"
	exit 1
fi

kubectl delete namespace $1