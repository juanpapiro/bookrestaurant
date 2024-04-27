package br.com.bookrestaurant.entity.restaurant.exception;

public class RestaurantInvalidException extends RuntimeException {

    public RestaurantInvalidException(String message) {
        super(message);
    }

}
