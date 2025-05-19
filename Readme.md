Запуск проекта. Должны находиться в корневой папке проекта.

1. Выполнить команду по сборке исходников для запуска приложения `./mvnw clean package`
<hr>

2. Выполнить команду по запуску docker-compose `docker-compose up -d`
<hr>

3. Выполнить команду по запуску sh-скрипта на добавление прав на топики `.\setup_kafka_acls.sh`
<hr>

URLs на создания message в topic:
1. http://localhost:8089/create-topic-1/{value}
2. http://localhost:8089/create-topic-2/{value}
<hr>

1. [setup_kafka_acls.sh](setup_kafka_acls.sh) Файл настроек прав на топики.

2. [cert](cert) В папке все сертификаты логически разделены по папкам.
