package br.com.bookrestaurant.infraestructure.gateway.impl.restaurant;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IRestaurantGateway;

import java.util.List;
import java.util.UUID;

public class RestaurantGateway implements IRestaurantGateway {

    private final IDataBase dataBase;

    public RestaurantGateway(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity) {
        return this.dataBase.registerRestaurant(restaurantEntity);
    }

    @Override
    public List<RestaurantEntity> findRestaurantByName(String name) {
        return this.dataBase.findRestaurantByName(name);
    }

    @Override
    public List<RestaurantEntity> findRestaurantByTypeOfCuisine(String typeOfCuisine) {
        return this.dataBase.findRestaurantByTypeOfCuisine(typeOfCuisine);
    }

    @Override
    public List<RestaurantEntity> findRestaurantByLocale(String uf, String city, String neighborhood) {
        return this.dataBase.findRestaurantByLocale(uf, city, neighborhood);
    }

    @Override
    public RestaurantEntity findRestaurantById(UUID id) {
        return this.dataBase.findRestaurantById(id);
    }

}
