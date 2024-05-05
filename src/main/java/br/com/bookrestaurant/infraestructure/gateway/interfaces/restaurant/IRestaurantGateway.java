package br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;

import java.util.List;
import java.util.UUID;

public interface IRestaurantGateway {

    RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity);
    List<RestaurantEntity> findRestaurantByName(String name);
    List<RestaurantEntity> findRestaurantByTypeOfCuisine(String typeOfCuisine);

    List<RestaurantEntity> findRestaurantByLocale(String uf, String city, String neighborhood);

    RestaurantEntity findRestaurantById(UUID id);

}
