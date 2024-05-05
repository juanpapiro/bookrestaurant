package br.com.bookrestaurant.entity.evaluate;


import br.com.bookrestaurant.entity.evaluate.exception.EvaluateInvalidException;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;

import java.util.Optional;
import java.util.UUID;

public class EvaluateEntityBuilder {

    private Optional<EvaluateEntity> evaluateOpt = Optional.ofNullable(null);

    public EvaluateEntityBuilder addInfos(String comment,
                                          Integer evaluation,
                                          RestaurantEntity restaurantEntity) {
        this.evaluateOpt = Optional.of(new EvaluateEntity(comment, evaluation, restaurantEntity));
        return this;
    }

    public EvaluateEntityBuilder addId(UUID evaluateId) {
        this.evaluateOpt.ifPresent(evaluate -> evaluate.setEvaluateId(evaluateId));
        return this;
    }

    public EvaluateEntity build() {
        return evaluateOpt.orElseThrow(() ->
                new EvaluateInvalidException("Falha ao criar nova avaliação de restaurante"));
    }

}
