FROM maven:3.9.6-eclipse-temurin-17 as builder

WORKDIR /app
COPY . .

RUN mvn clean package

# Resultado: target/lambda-java.jar