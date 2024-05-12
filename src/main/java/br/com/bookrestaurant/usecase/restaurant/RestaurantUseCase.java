package br.com.bookrestaurant.usecase.restaurant;

import br.com.bookrestaurant.entity.EntityUtil;
import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntityBuilder;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantNotFoundException;

import java.util.*;

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
        EntityUtil.isNull(address, "Endereço é obrigatório");
    }

    private static void validOpeningHours(List<OpeningHour> openingHours) {
        EntityUtil.isNull(openingHours, "Horário de funcionamento é obrigatório");
        if (openingHours != null && openingHours.size() > 7)
            throw new RestaurantInvalidException("Lista de horários de funcionamento deve conter no máximo 7 dias");

        if (openingHours != null && openingHours.size() != openingHours.stream().distinct().toList().size())
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

    public static String findByTypeOfCuisine(String typeOfCuisine) {
        return validParam(typeOfCuisine, 4, "Tipo de cozinha inválido!");
    }

    public static Map<String,String> findByTypeOfCuisine(String uf, String city, String neighborhood) {
        Map<String, String> params = new HashMap<>();
        params.put("uf", uf);
        params.put("city", city);
        params.put("neighborhood", neighborhood);
        EntityUtil.isNull(params, "UF ou cidade ou bairro deve ser informado.");
        params.entrySet().forEach(el ->
            el.setValue(el.getValue() == null ? "" : el.getValue().trim().toLowerCase())
        );
        return params;
    }

    public static UUID findById(UUID id) {
        return EntityUtil.isNull(id,"Id do restaurante é obrigatório!");
    }

    private static String validParam(String value, int size, String message) {
        return Optional.ofNullable(value)
                .map(String::trim)
                .filter(v -> !v.isBlank())
                .filter(v -> v.length() >= size)
                .map(String::toLowerCase)
                .orElseThrow(() -> new RestaurantInvalidException(message));
    }

    private RestaurantUseCase() {
        throw new IllegalStateException("Class util not instance.");
    }
}
