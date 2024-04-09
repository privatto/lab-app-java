#!/bin/bash
#set -e

docker_path=$(command -v docker)
if [ -x "$docker_path" ]; then
    ENGINE="docker"
else
    podman_path=$(command -v podman)
    if [ -x "$podman_path" ]; then
        ENGINE="podman"
    else
        echo "No container engine found; Install docker or podman"
        exit 1
    fi
fi  

$ENGINE build -t lab-app-java . || true
$ENGINE run -dit --name lab-app-java -p 8080:8080 lab-app-java || true
$ENGINE ps