# BOOKRESTAURANT - API

## Execução de testes com make

- Para executar testes unitários e testes integrados
```sh
make test
```
- para executar testes de sistema:
```sh
mvn system-test
```

## Iniciar aplicação com make

- Para executar aplicação com imagem docker e container na porta 8015:
```sh
make start-app-docker
```
- Para finalizar aplicação:
```sh
make stop-app-docker
```

## Execução de testes

- Para executar testes unitários
```sh
mvn test
```
- Para executar testes integrados
```sh
mvn test -P it-test
```
- para executar testes de sistema:
```sh
mvn test -P sys-test
```
- para executar apenas smoke test testes de sistema:
```sh
mvn test -P system-test -D cucumber.filter.tags="@smoke"
```
- para instalar allure cli:
```sh
mpm install -g allure
```
- para vislualizar report de testes com allure:
```sh
allure serve target/allure-results
```