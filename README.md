# Chatbot

## Descrição
Projeto criado para implementar chatbots com diferentes implementações: Socket e RMI

## Execução

```bash
# para compilar os arquivos
javac src/main/java/ufrn/br/dim0614/*.java

# para executar o server (socket ou rmi deve ser passado como parâmetro)
java -cp src/main/java ufrn.br.dim0614.ChatBotServer socket

# para executar o client (socket ou rmi deve ser passado como parâmetro)
java -cp src/main/java ufrn.br.dim0614.ChatBotClient socket

# após a integração com a lib do chatbot, basta rodar (socket ou rmi deve ser passado como parâmetro)
java -cp target/classes;lib/program-ab-0.0.4.3/lib/Ab.jar ufrn.br.dim0614.ChatBotServer socket

java -cp target/classes;lib/program-ab-0.0.4.3/lib/Ab.jar ufrn.br.dim0614.ChatBotServer socket

```
