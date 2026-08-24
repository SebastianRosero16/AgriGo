#!/bin/bash

echo "Building all microservices..."

cd common && mvn clean install -DskipTests && cd ..
cd auth-service && mvn clean package -DskipTests && cd ..
cd farmer-service && mvn clean package -DskipTests && cd ..
cd store-service && mvn clean package -DskipTests && cd ..
cd payment-service && mvn clean package -DskipTests && cd ..
cd ai-recommendation-service && mvn clean package -DskipTests && cd ..
cd product-marketplace-service && mvn clean package -DskipTests && cd ..
cd inventory-service && mvn clean package -DskipTests && cd ..
cd notification-service && mvn clean package -DskipTests && cd ..

echo "All services built successfully!"
