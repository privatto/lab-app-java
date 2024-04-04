#!/bin/bash
set -e

docker stop lab-app-java
docker rm lab-app-java
docker rmi lab-app-java
