package br.com.bookrestaurant.infraestructure.gateway.impl.evaluate;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.evaluate.IEvaluateGateway;

public class EvaluateGateway implements IEvaluateGateway {

    private IDataBase dataBase;

    public EvaluateGateway(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public EvaluateEntity registryEvaluate(EvaluateEntity evaluateEntity) {
        return dataBase.registryEvaluate(evaluateEntity);
    }

}
