package br.com.bookrestaurant.entity.restaurant.exception;

public class RestaurantNotFoundException extends RuntimeException {

//    public RestaurantNotFoundException(String message) {
//        super(message);
//    }

    public RestaurantNotFoundException() {
        super("Restaurante não localizado!");
    }

}
