docker exec kafka-1 kafka-acls --bootstrap-server kafka-1:9093 --add --allow-principal User:* --operation Read --operation Write --topic topic-1

docker exec kafka-1 kafka-acls --bootstrap-server kafka-1:9093 --add --allow-principal User:* --operation Write --topic topic-2

docker exec kafka-1 kafka-acls --bootstrap-server kafka-1:9093 --add --deny-principal User:* --operation Read --topic topic-2


./setup_kafka_acls.sh

docker exec -i kafka-1 bash -c "kafka-acls --bootstrap-server kafka-1:9093 --command-config /etc/kafka/jaas/client_sasl.properties --add --allow-principal User:'*' --operation Read --operation Write --topic topic-1; exit"

docker exec -i kafka-1 bash -c "kafka-acls --bootstrap-server kafka-1:9093 --command-config /etc/kafka/jaas/client_sasl.properties --add --allow-principal User:'*' --operation Read --topic topic-2; exit"Read

docker exec -i kafka-1 bash -c "kafka-acls --bootstrap-server kafka-1:9093 --command-config /etc/kafka/jaas/client_sasl.properties --add --allow-principal User:'*' --operation Write --topic topic-2; exit"