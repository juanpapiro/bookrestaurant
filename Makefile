build:
	mvn compile

unit-test:
	mvn test

integration-test:
	mvn test -P it-test

test: unit-test integration-test

system-test:
	@echo "iniciando teste de sistema"
	@mvn test -P sys-test

start-app-system-test: start-app-docker
	@echo "iniciando teste de sistema"
	@mvn test -P sys-test
	@echo "Teste de sistema finalizado"
	@echo "finalizando containers"
	@make stop-app-docker

start-app-performance-test: start-app-docker
	@echo "iniciando teste de performance"
	@mvn gatling:test -P performance-test
	@echo "Teste de performance finalizado"
	@echo "Finalizando containers"
	@make stop-app-docker

performance-test:
	mvn gatling:test -P performance-test

start-db:
	docker-compose -f docker-compose-db.yml up -d

stop-db:
	docker-compose -f docker-compose-db.yml down

start-app:
	mvn spring-boot:run -D spring-boot.run.profiles=local

start: start-db start-app

package:
	mvn package

docker-build: #package
	docker build . -t bookrestaurant:test -f ./Dockerfile

start-app-docker: docker-build
	docker-compose -f docker-compose-app.yml up -d

stop-app-docker:
	docker-compose -f docker-compose-app.yml down

allure-start:
	@echo "Exibir relatorio de testes unitarios e integrados com allure-results"
	allure serve target/allure-results