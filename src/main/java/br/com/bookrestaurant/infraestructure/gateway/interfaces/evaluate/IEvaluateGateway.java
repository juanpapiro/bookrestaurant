package br.com.bookrestaurant.infraestructure.gateway.interfaces.evaluate;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;

public interface IEvaluateGateway {

    EvaluateEntity registryEvaluate(EvaluateEntity evaluateEntity);

}
