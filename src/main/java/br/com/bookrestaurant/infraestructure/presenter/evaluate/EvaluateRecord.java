package br.com.bookrestaurant.infraestructure.presenter.evaluate;

import java.util.UUID;

public record EvaluateRecord(String comment, Integer evaluation, UUID restaurantId) {
}
