#!/bin/bash
./gradlew clean build
docker image build --platform linux/amd64 -t ihudak/dt-payment-service:latest .
docker push ihudak/dt-payment-service:latest

docker image build --platform linux/arm64 -t ihudak/dt-payment-service:arm64 .
docker push ihudak/dt-payment-service:arm64
