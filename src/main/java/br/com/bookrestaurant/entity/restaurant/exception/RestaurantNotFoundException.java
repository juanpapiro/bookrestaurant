package br.com.bookrestaurant.entity.restaurant.exception;

public class RestaurantNotFoundException extends RuntimeException {

    public RestaurantNotFoundException() {
        super("Restaurante não localizado!");
    }

}
