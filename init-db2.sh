#!/bin/bash

echo "##########################################Aguardando o DB2 iniciar..."
until su - db2inst1 -c "db2start" &>/dev/null; do
  echo "Esperando DB2..."
  sleep 5
done
echo "DB2 iniciado!"

echo "Aguardando o usuário db2inst1 ser criado..."
while ! id "db2inst1" &>/dev/null; do
  sleep 5
done
echo "Usuário db2inst1 encontrado!"

chmod -R 777 ./database/data

# Conectar ao banco
su - db2inst1 -c "db2 CREATE DATABASE zoo AUTOMATIC STORAGE YES;"
su - db2inst1 -c "db2 CONNECT TO zoo;"
su - db2inst1 -c "db2 GRANT DBADM ON DATABASE TO USER db2inst1;"
su - db2inst1 -c "db2 list db directory"

echo "Banco de dados inicializado com sucesso!"
