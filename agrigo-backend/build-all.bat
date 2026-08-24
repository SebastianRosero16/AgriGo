@echo off
echo Building all microservices...

cd common
call mvn clean install -DskipTests
cd ..

cd auth-service
call mvn clean package -DskipTests
cd ..

cd farmer-service
call mvn clean package -DskipTests
cd ..

cd store-service
call mvn clean package -DskipTests
cd ..

cd payment-service
call mvn clean package -DskipTests
cd ..

cd ai-recommendation-service
call mvn clean package -DskipTests
cd ..

cd product-marketplace-service
call mvn clean package -DskipTests
cd ..

cd inventory-service
call mvn clean package -DskipTests
cd ..

cd notification-service
call mvn clean package -DskipTests
cd ..

echo All services built successfully!
pause
