package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.AddressGateway;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.OpeningHourGateway;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.RestaurantGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IAddressGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IOpeningHourGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IRestaurantGateway;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.OpeningHourRecord;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.RestaurantRecord;
import br.com.bookrestaurant.usecase.restaurant.RestaurantUseCase;

import java.util.List;
import java.util.Map;

public class RestaurantController {

    public RestaurantEntity register(
            RestaurantRecord restaurantRecord,
            AddressRecord addressRecord,
            List<OpeningHourRecord> openingHoursRecords,
            IDataBase database){

        IRestaurantGateway restaurantGateway = new RestaurantGateway(database);
        IOpeningHourGateway openingHourGateway = new OpeningHourGateway(database);
        IAddressGateway addressGateway = new AddressGateway(database);

        List<OpeningHour> openingHours = openingHoursRecords.stream()
                .map(rec -> new OpeningHour(rec.dayOfTheWeekCode(), rec.hourOpen(), rec.hourClose()))
                .toList();

        Address address = new Address(addressRecord.street(), addressRecord.number(),
                addressRecord.neighborhood(), addressRecord.city(), addressRecord.uf(), addressRecord.cep());

        RestaurantEntity restaurantEntity = RestaurantUseCase.registerRestaurant(restaurantRecord.name(),
                restaurantRecord.typeOfCuisine(), restaurantRecord.capacity(), address, openingHours);

        restaurantEntity = restaurantGateway.registerRestaurant(restaurantEntity);
        addressGateway.registerAddress(address, restaurantEntity.getId());
        openingHourGateway.registerOpeningHours(openingHours, restaurantEntity.getId());

        return restaurantEntity;
    }

    public List<RestaurantEntity> findByName(String name, IDataBase database) {
        name = RestaurantUseCase.findByName(name);
        List<RestaurantEntity> restaurants = database.findRestaurantByName(name);
        return RestaurantUseCase.locateRestaurants(restaurants);
    }

    public List<RestaurantEntity> findByTypeOfCuisine(String typeOfCuisine, IDataBase database) {
        typeOfCuisine = RestaurantUseCase.findByTypeOfCuisine(typeOfCuisine);
        List<RestaurantEntity> restaurants = database.findRestaurantByTypeOfCuisine(typeOfCuisine);
        return RestaurantUseCase.locateRestaurants(restaurants);
    }

    public List<RestaurantEntity> findByLocale(String uf, String city, String neighborhood, IDataBase database) {
        Map<String,String> params = RestaurantUseCase.findByTypeOfCuisine(uf, city, neighborhood);
        List<RestaurantEntity> restaurants = database.findRestaurantByLocale(params.get("uf"), params.get("city"), params.get("neighborhood"));
        return RestaurantUseCase.locateRestaurants(restaurants);
    }

}
