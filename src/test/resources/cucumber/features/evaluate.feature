# language: pt

Funcionalidade: Avaliar restaurante

  Cenário: Avaliar um restaurante
    Dado que um restaurante já foi registrado
    E uma avaliação sobre ele for registrada
    Então a avaliação é registrada com sucesso
    E avaliação deve ser apresentada

  Cenário: Avaliar um restaurante inexistente
    Quando feita uma nova solicitação de registro de avaliação
    E nenhum restaurante correspondente for localizado
    Então a avaliação não é registrada com sucesso