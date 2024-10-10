# Coupon Management Service

## Overview

The Coupon Management Service is a RESTful API designed to manage coupon codes within a system. It allows users to store, claim, and retrieve the status of coupons efficiently. The service ensures that each device can claim only one coupon, and each coupon can be claimed by only one device. It also handles coupon expiry times effectively.

## Features

- Store new coupons with unique codes and expiry times.
- Claim coupons using a device ID.
- Retrieve the status of specific coupons.
- Check the status of coupons claimed by specific devices.
- Get information on all available coupons.

## Technologies Used

- **Java 17**: Programming language used for the application.
- **Spring Boot 2.7.0**: Framework for building the API.
- **Swagger/OpenAPI**: API documentation.
- **DynamoDB**: Database for storing coupon information.
- **Kubernetes**: Container orchestration for deployment.
- **Docker**: Containerization of the application.
- **Gradle**: Build automation tool.

## API Endpoints

| Endpoint                                | Method | Description                               |
|-----------------------------------------|--------|-------------------------------------------|
| `/v1/coupons`                           | POST   | Store a new coupon                        |
| `/v1/coupons/claim`                     | GET    | Claim a coupon                            |
| `/v1/coupons/{couponCode}/status`       | GET    | Get status of a specific coupon           |
| `/v1/coupons/device/{deviceId}/status`  | GET    | Get status of coupons claimed by a device |
| `/v1/coupons/all`                       | GET    | Get information on all coupons            |

## Getting Started

### Prerequisites

- Java 17
- Gradle
- Docker
- Kubernetes (Minikube)
- AWS CLI (for DynamoDB setup)

### Installation and Deployment Steps

1. **Start Minikube**:

   ```bash
   minikube start
   ```

2. **Set Environment Variables**:

   ```bash
   eval $(minikube docker-env)
   ```

3. **Build the Application**:

   ```bash
   ./gradlew build
   ```

4. **Build Docker Image**:

   ```bash
   docker build -t coupon-app:latest .
   ```

5. **Run Docker Registry**:

   ```bash
   docker run -d -p 5000:5000 --restart=always --name registry registry:2
   ```

6. **Tag Docker Image**:

   ```bash
   docker tag coupon-app:latest localhost:5000/coupon-app:latest
   ```

7. **Push Docker Image**:

   ```bash
   docker push localhost:5000/coupon-app:latest
   ```

8. **Deploy DynamoDB**:

   ```bash
   kubectl apply -f dynamodb-deployment.yaml
   ```

9. **Deploy Coupon Management Application**:

   ```bash
   kubectl apply -f coupon-app-deployment.yaml
   ```

10. **Port Forward for Local DynamoDB**:

    ```bash
    kubectl port-forward service/dynamodb-local 8000:8000
    ```

11. **Create DynamoDB Table**:

    ```bash
    aws dynamodb create-table --table-name Coupons --attribute-definitions AttributeName=couponCode,AttributeType=S --key-schema AttributeName=couponCode,KeyType=HASH --provisioned-throughput ReadCapacityUnits=5,WriteCapacityUnits=5 --endpoint-url http://localhost:8000
    ```

12. **Access the Application**:

    ```bash
    minikube service coupon-app --url
    ```

## Running Locally

To run the application locally without Docker or Kubernetes, use the command:

```bash
./gradlew bootRun
```

The application will be accessible at `http://localhost:8080`.

### API Documentation

The API documentation is generated using Swagger/OpenAPI. You can access it at:

```bash
http://localhost:8080/v3/api-docs
```

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Contact

For any inquiries, please contact:

- **Name**: Sahanna Rachel
- **Email**: sahannarachel.john@xperi.com
- **GitHub**: [sahannarachel-xperi](https://github.com/sahannarachel-xperi)
