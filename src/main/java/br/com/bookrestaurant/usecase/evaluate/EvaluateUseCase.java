package br.com.bookrestaurant.usecase.evaluate;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.entity.evaluate.EvaluateEntityBuilder;
import br.com.bookrestaurant.entity.evaluate.exception.EvaluateInvalidException;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;

import java.util.Optional;
import java.util.UUID;

public class EvaluateUseCase {

    public static EvaluateEntity register(String comment,
                                          Integer evaluation,
                                          UUID restaurantId,
                                          RestaurantEntity restaurantEntity) {
        restaurantEntity = validLocateRestaurant(restaurantId, restaurantEntity);
        validEvaluation(evaluation);
        return new EvaluateEntityBuilder().addInfos(comment, evaluation, restaurantEntity).build();
    }

    private static RestaurantEntity validLocateRestaurant(UUID restaurantId, RestaurantEntity restaurantEntity) {
        return Optional.ofNullable(restaurantEntity)
                .filter(restaurant -> restaurant.getId().equals(restaurantId))
                .orElseThrow(() -> new EvaluateInvalidException("Restaurante avaliado não existe"));
    }

    private static void validEvaluation(Integer evaluation) {
        if (evaluation < 1 || evaluation > 5) {
            throw new EvaluateInvalidException("Nota de avaliação deve ser de 1 a 5");
        }
    }

}
