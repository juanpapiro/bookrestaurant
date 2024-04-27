package br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant;

import br.com.bookrestaurant.entity.restaurant.OpeningHour;

import java.util.List;
import java.util.UUID;

public interface IOpeningHourGateway {

    OpeningHour registerOpeningHour(OpeningHour openingHour, UUID restaurantId);
    List<OpeningHour> registerOpeningHours(List<OpeningHour> openingHours, UUID restaurantId);

}
