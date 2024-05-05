package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.infraestructure.gateway.impl.evaluate.EvaluateGateway;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.RestaurantGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.evaluate.IEvaluateGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IRestaurantGateway;
import br.com.bookrestaurant.infraestructure.presenter.evaluate.EvaluateRecord;
import br.com.bookrestaurant.usecase.evaluate.EvaluateUseCase;
import br.com.bookrestaurant.usecase.restaurant.RestaurantUseCase;

import java.util.UUID;

public class EvaluateController {

    public EvaluateEntity register(EvaluateRecord evaluateRecord,
                                   IDataBase database) {

        IEvaluateGateway evaluateGateway = new EvaluateGateway(database);
        IRestaurantGateway restaurantGateway = new RestaurantGateway(database);

        UUID restaurantId = RestaurantUseCase.findById(evaluateRecord.restaurantId());
        RestaurantEntity restaurantEntity = restaurantGateway.findRestaurantById(restaurantId);

        EvaluateEntity evaluateEntity = EvaluateUseCase.register(evaluateRecord.comment(),
                evaluateRecord.evaluation(), evaluateRecord.restaurantId(), restaurantEntity);
        evaluateEntity = evaluateGateway.registryEvaluate(evaluateEntity);

        return evaluateEntity;
    }

}
