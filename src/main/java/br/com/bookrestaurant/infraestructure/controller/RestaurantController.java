package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntityBuilder;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.AddressGateway;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.OpeningHourGateway;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.RestaurantGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IAddressGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IOpeningHourGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IRestaurantGateway;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.OpeningHourRecord;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.RestaurantRecord;
import br.com.bookrestaurant.usecase.restaurant.RestarantUseCase;

import java.util.List;

public class RestaurantController {

    public RestaurantEntity register(
            RestaurantRecord restaurantRecord,
            AddressRecord addressRecord,
            List<OpeningHourRecord> openingHoursRecords,
            IDataBase database){

        IRestaurantGateway restaurantGateway = new RestaurantGateway(database);
        IOpeningHourGateway openingHourGateway = new OpeningHourGateway(database);
        IAddressGateway addressGateway = new AddressGateway(database);
//        RestarantUseCase restarantUseCase = new RestarantUseCase();

        List<OpeningHour> openingHours = openingHoursRecords.stream()
                .map(rec -> new OpeningHour(rec.dayOfTheWeekCode(), rec.hourOpen(), rec.hourClose()))
                .toList();

        Address address = new Address(addressRecord.street(), addressRecord.number(),
                addressRecord.neighborhood(), addressRecord.city(), addressRecord.uf(), addressRecord.cep());

        RestaurantEntity restaurantEntity = RestarantUseCase.registerRestaurant(restaurantRecord.name(),
                restaurantRecord.typeOfCusine(), restaurantRecord.capacity(), address, openingHours);

        restaurantEntity = restaurantGateway.registerRestaurant(restaurantEntity);
        addressGateway.registerAddress(address, restaurantEntity.getId());
        openingHourGateway.registerOpeningHours(openingHours, restaurantEntity.getId());

        return restaurantEntity;
    }

}
