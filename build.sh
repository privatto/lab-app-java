#!/bin/bash
set -e

docker build -t lab-app-java .
docker run -dit --name lab-app-java -p 8080:8080 lab-app-java

docker ps