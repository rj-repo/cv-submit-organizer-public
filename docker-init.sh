#!/bin/bash
mvn clean install -DskipTests -Dspring.profiles.active=dev
docker compose build
docker compose up