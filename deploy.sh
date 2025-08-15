#!/bin/bash

FUNCTION_NAME="LambdaProcesseFile"
BUCKET_NAME="lambda-test-java-course"
JAR_NAME="lambda-process-file.jar"
ROLE_ARN="arn:aws:iam::022660490942:user/developer"
HANDLER="org.example.ExecutionHandler::handleRequest"

# Build
echo "Compilando dados"
docker build -t lambda-process-file-builder .

# Copia o JAR do container
echo "Estraindo JAR..."
CONTAINER_ID=$(docker create lambda-process-file-builder)
docker cp $CONTAINER_ID:/app/target/$JAR_NAME ./
docker rm $CONTAINER_ID

# Envia para o S3
echo "Enviando para S3..."
AWS S3 CP $JAR_NAME s3://$BUCKET_NAME/$JAR_NAME

# Atualiza ou cria a função Lambda
echo "Atualizando Lambda..."
aws lambda update-function-code \
  --funtion-name $FUNCTION_NAME \
  --s3-bucket $BUCKET_NAME \
  --s3-key $JAR_NAME || \
aws lambda create-function \
  --function-name $FUNCTION_NAME \
  --runtime java17 \
  --role $ROLE_ARN \
  --handler $HANDLER \
  --code S3Bucket=$BUCKET_NAME,S3Key=$JAR_NAME