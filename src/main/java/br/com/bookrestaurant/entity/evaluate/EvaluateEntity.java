package br.com.bookrestaurant.entity.evaluate;

import br.com.bookrestaurant.entity.EntityUtil;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class EvaluateEntity {

	private UUID evaluateId;
    private String comment;
    private Integer evaluation;
    private RestaurantEntity restaurantEntity;
    private LocalDateTime dateCreate;

    public EvaluateEntity(String comment, Integer evaluation, RestaurantEntity restaurantEntity) {
        this.comment = EntityUtil.isNullOrBlank(comment, "Comentário é obrigatório");
        this.evaluation = EntityUtil.isNull(evaluation, "Nota de avaliação é obrigatória");
        this.restaurantEntity = EntityUtil.isNull(restaurantEntity, "Restaurante inexistente");
        this.dateCreate = LocalDateTime.now();
    }

    public UUID getEvaluateId() {
        return evaluateId;
    }

    public void setEvaluateId(UUID evaluateId) {
        this.evaluateId = evaluateId;
    }

    public String getComment() {
        return comment;
    }

    public Integer getEvaluation() {
        return evaluation;
    }

    public RestaurantEntity getRestaurantEntity() {
        return restaurantEntity;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

}
