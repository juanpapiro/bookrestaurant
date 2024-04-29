package br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;

import java.util.List;

public interface IRestaurantGateway {

    RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity);
    List<RestaurantEntity> findByName(String name);
    List<RestaurantEntity> findByTypeOfCuisine(String typeOfCuisine);

    List<RestaurantEntity> findByLocale(String uf, String city, String neighborhood);

}
