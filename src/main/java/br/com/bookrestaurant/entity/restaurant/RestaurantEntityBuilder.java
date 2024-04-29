package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;

import java.util.List;
import java.util.Optional;

public class RestaurantEntityBuilder {

    private Optional<RestaurantEntity> restaurantEntity = Optional.ofNullable(null);

    public RestaurantEntityBuilder addInfos(String name, String typeOfCuisine, Integer capacity) {
        this.restaurantEntity = Optional.of(new RestaurantEntity(name, typeOfCuisine, capacity));
        return this;
    }

    public RestaurantEntityBuilder addAddress(Address address) {
        restaurantEntity.ifPresent(rest -> rest.setAddress(address));
        return this;
    }

     public RestaurantEntityBuilder addOpeningHours(List<OpeningHour> openingHours) {
         restaurantEntity.ifPresent(rest -> rest.setOpeningHours(openingHours));
         return this;
    }

    public RestaurantEntity build() {
        return restaurantEntity.orElseThrow(() ->
                new RestaurantInvalidException("Falha ao criar novo restaurante"));
    }


}
