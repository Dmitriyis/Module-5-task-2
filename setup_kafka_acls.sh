#!/bin/bash
docker exec -i kafka-1 bash -c "\
  kafka-acls --bootstrap-server kafka-1:9092 \
  --command-config /etc/kafka/jaas/client_sasl.properties \
  --add --allow-principal User:consumer \
  --operation Describe \
  --topic topic-1"

docker exec -i kafka-1 bash -c "\
  kafka-acls --bootstrap-server kafka-1:9092 \
  --command-config /etc/kafka/jaas/client_sasl.properties \
  --add --allow-principal User:consumer \
  --operation Describe \
  --topic topic-2"

docker exec -i kafka-1 bash -c "\
  kafka-acls --bootstrap-server kafka-1:9092 \
  --command-config /etc/kafka/jaas/client_sasl.properties \
  --add --allow-principal User:producer \
  --operation Describe \
  --topic topic-1"

docker exec -i kafka-1 bash -c "\
  kafka-acls --bootstrap-server kafka-1:9092 \
  --command-config /etc/kafka/jaas/client_sasl.properties \
  --add --allow-principal User:producer \
  --operation Describe \
  --topic topic-2"

docker exec -i kafka-1 bash -c "\
  kafka-acls --bootstrap-server kafka-1:9092 \
  --command-config /etc/kafka/jaas/client_sasl.properties \
  --add --allow-principal User:producer \
  --operation Write \
  --topic topic-1"

docker exec -i kafka-1 bash -c "\
  kafka-acls --bootstrap-server kafka-1:9092 \
  --command-config /etc/kafka/jaas/client_sasl.properties \
  --add --allow-principal User:producer \
  --operation Write \
  --topic topic-2"

docker exec -i kafka-1 bash -c "\
  kafka-acls --bootstrap-server kafka-1:9092 \
  --command-config /etc/kafka/jaas/client_sasl.properties \
  --add --allow-principal User:consumer \
  --operation Read \
  --group topic-1"

docker exec -i kafka-1 bash -c "\
  kafka-acls --bootstrap-server kafka-1:9092 \
  --command-config /etc/kafka/jaas/client_sasl.properties \
  --add --allow-principal User:consumer \
  --operation Read \
  --topic topic-1"

sleep 5 && docker restart app



