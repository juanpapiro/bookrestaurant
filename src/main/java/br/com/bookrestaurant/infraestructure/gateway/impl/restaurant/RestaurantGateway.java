package br.com.bookrestaurant.infraestructure.gateway.impl.restaurant;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IRestaurantGateway;

public class RestaurantGateway implements IRestaurantGateway {

    private final IDataBase dataBase;

    public RestaurantGateway(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public RestaurantEntity registerRestaurant(RestaurantEntity restaurantEntity) {
        return dataBase.registerRestaurant(restaurantEntity);
    }

}
