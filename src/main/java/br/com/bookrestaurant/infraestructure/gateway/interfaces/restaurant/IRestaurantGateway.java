package br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;

public interface IRestaurantGateway {

    RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity);

}
