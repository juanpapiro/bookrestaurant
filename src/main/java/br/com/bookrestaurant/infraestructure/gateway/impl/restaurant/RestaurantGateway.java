package br.com.bookrestaurant.infraestructure.gateway.impl.restaurant;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IRestaurantGateway;

import java.util.List;

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
    public List<RestaurantEntity> findByName(String name) {
        return this.dataBase.findByName(name);
    }

    @Override
    public List<RestaurantEntity> findByTypeOfCuisine(String typeOfCuisine) {
        return this.dataBase.findByTypeOfCuisine(typeOfCuisine);
    }

    @Override
    public List<RestaurantEntity> findByLocale(String uf, String city, String neighborhood) {
        return this.dataBase.findByLocale(uf, city, neighborhood);
    }

}
