# language: pt

Funcionalidade: Registrar Restaurante

  Cenário: Registrar restaurante
    Quando registrar um novo restaurante
    Então o restaurante é registrado com sucesso
    E deve ser apresentado

  Cenário: Buscar restaurante por nome
    Dado que um restaurante já foi registrado
    Quando efetuar a busca do restaurante por nome
    Então o restaurante é exibido com sucesso

  Cenário: Buscar restaurante por nome com um dígito
    Dado que um restaurante já foi registrado
    Quando efetuar a busca do restaurante por nome inválido
    Então o response status é bad request

  Cenário: Buscar restaurante por nome inexistente
    Dado que um restaurante já foi registrado
    Quando efetuar a busca do restaurante por nome inexistente
    Então o response status é not found

  Cenário: Buscar restaurante por tipo de cozinha
    Dado que um restaurante já foi registrado
    Quando efetuar a busca do restaurante por tipo de cozinha
    Então o restaurante é exibido com sucesso

  Cenário: Buscar restaurante por localização
    Dado que um restaurante já foi registrado
    Quando efetuar a busca do restaurante por uf ou cidade ou bairro
    Então o restaurante é exibido com sucesso

