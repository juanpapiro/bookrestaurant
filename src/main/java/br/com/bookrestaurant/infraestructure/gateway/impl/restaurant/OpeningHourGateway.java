package br.com.bookrestaurant.infraestructure.gateway.impl.restaurant;

import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IOpeningHourGateway;

import java.util.List;
import java.util.UUID;

public class OpeningHourGateway implements IOpeningHourGateway {

    private final IDataBase dataBase;

    public OpeningHourGateway(IDataBase dataBase) {
        this.dataBase = dataBase;
    }

    @Override
    public OpeningHour registerOpeningHour(OpeningHour openingHour, UUID restaurantId) {
        return dataBase.registerOpeningHour(openingHour, restaurantId);
    }

    @Override
    public List<OpeningHour> registerOpeningHours(List<OpeningHour> openingHours, UUID restaurantId) {
        return dataBase.registerOpeningHours(openingHours, restaurantId);
    }


}
