package br.com.bookrestaurant.usecase.restaurant;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntityBuilder;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class RestaurantUseCase {

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


    public static List<RestaurantEntity> locateRestaurants(List<RestaurantEntity> restaurants) {
        return Optional.ofNullable(restaurants)
                .filter(list -> !list.isEmpty())
                .orElseThrow(RestaurantNotFoundException::new);
    }

    public static String findByName(String name) {
        return validParam(name, 2, "Nome inválido!");
    }

    public static String findByLocale(String typeOfCuisine) {
        return validParam(typeOfCuisine, 4, "Tipo de cozinha inválido!");
    }

    public static Map<String,String> findByLocale(String uf, String city, String neighborhood) {
        Map<String, String> params = new HashMap<>();
        params.put("uf", uf);
        params.put("city", city);
        params.put("neighborhood", neighborhood);
        params.entrySet().stream().filter(el -> el.getValue() != null)
                .findFirst()
                .orElseThrow(() -> new RestaurantInvalidException("UF ou cidade ou bairro deve ser informado."));
        params.entrySet().forEach(el -> {
            el.setValue(el.getValue() == null ? "" : el.getValue().trim().toLowerCase());
        });
        return params;
    }

    private static String validParam(String value, int size, String message) {
        return Optional.ofNullable(value)
                .map(v -> v.trim())
                .filter(v -> !v.isBlank())
                .filter(v -> v.length() >= size)
                .map(v -> v.toLowerCase())
                .orElseThrow(() -> new RestaurantInvalidException(message));
    }


}