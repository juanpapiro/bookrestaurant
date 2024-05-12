# BOOKRESTAURANT - API

## Execução de testes com make

- Para executar testes unitários e testes integrados
```sh
make test
```
- Para executar testes de sistema iniciando e finalizando app:
```sh
make start-app-system-test
```
- Para executar teste de performance iniciando e finalizando app:
```sh
make start-app-performance-test
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

## Execução de testes com mvn

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

## Exemplos de requests
- Registrar restaurante
```sh
curl -X "POST" --location "https://bookrestaurant.onrender.com/restaurant" -H "Content-Type: application/json" --data "{\"name\":\"Zucco Cucina\",\"typeOfCuisine\":\"Chilena\",\"capacity\":50,\"address\":{\"street\":\"Rua Urca\",\"number\":60,\"neighborhood\":\"Jardim Vazame\",\"city\":\"Embu\",\"uf\":\"SP\",\"cep\":\"06826-260\"},\"openingHours\":[{\"dayOfTheWeekCode\":1,\"hourOpen\":\"10:00:00\",\"hourClose\":\"22:00:00\"}]}"
```
- Buscar restaurante por nome
```sh
curl -X "GET" --location "https://bookrestaurant.onrender.com/restaurant/by-name?name=Mama"
```
- Buscar restaurante por localização
```sh
curl -X "GET" --location "https://bookrestaurant.onrender.com/restaurant/by-locale?uf=SP&city=Embu&neighborhood=Jardim%20S%C3%A3o%20Vicente"
```
- Buscar restaurante por tipo de cozinha
```sh
curl -X "GET" --location "https://bookrestaurant.onrender.com/restaurant/by-type-of-cuisine?typeOfCuisine=Itali"
```
- Avaliação de restaurante
```sh
curl -X "POST" --location "https://bookrestaurant.onrender.com/evaluate" --header "Content-Type: application/json" --data "{\"comment\":\"Comida muito boa\",\"evaluation\": 4,\"restaurantId\":\"61d723bb-7ead-4f23-82c4-14d34155d9d6\"}"
```
- Registro de reserva em restaurante
```sh
curl -X "POST" --location "https://bookrestaurant.onrender.com/reserve" --header "Content-Type: application/json" --data "{\"restaurantId\":\"61d723bb-7ead-4f23-82c4-14d34155d9d6\",\"date\":\"2024-05-12T10:00:00\",\"seats\": 4,\"status\": \"A\",\"client\": {\"name\": \"Jose\",\"phone\": 984688555}}"
```
- Busca de reserva em restaurante
```sh
curl -X "GET" --location "https://bookrestaurant.onrender.com/reserve/by-restaurant-and-date?restaurantId=61d723bb-7ead-4f23-82c4-14d34155d9d6&date=2024-05-12T10:00:00"
```
- Atualização de reserva em restaurante
```sh
curl -X "PUT" --location "https://bookrestaurant.onrender.com/reserve?id=05cf5c3a-d8d1-45ad-a149-aa58c685bc86&status=F"
```