package br.com.bookrestaurant.usecase.restaurant;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntityBuilder;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;

import java.util.List;
import java.util.Optional;

public class RestarantUseCase {

    public static RestaurantEntity registerRestaurant(String name, String typeOdCousine, Integer capacity,
                                     Address address, List<OpeningHour> openingHours) {
        validOpeningHours(openingHours);
        validAddress(address);
        return new RestaurantEntityBuilder()
                .addInfos(name, typeOdCousine, capacity)
                .addAddress(address)
                .addOpeningHours(openingHours)
                .build();
    }

    private static void validAddress(Address address) {
        Optional.ofNullable(address)
                .orElseThrow(() -> new RestaurantInvalidException("Endereço é obrigatório"));
    }

    private static void validOpeningHours(List<OpeningHour> openingHours) {
        Optional.ofNullable(openingHours)
                .filter(l -> !l.isEmpty())
                .orElseThrow(() -> new RestaurantInvalidException("Horário de funcionamento é obrigatório"));

        if (openingHours.size() > 7)
            throw new RestaurantInvalidException("Lista de horários de funcionamento deve conter no máximo 7 dias");

        if (openingHours.size() != openingHours.stream().distinct().toList().size())
            throw new RestaurantInvalidException("Lista de horários de funcionamento não deve conter dias repetidos");
    }

}
