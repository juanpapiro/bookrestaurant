package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class RestaurantModelTest {

    @Test
    void testInstance() {
        UUID id = UUID.randomUUID();
        RestaurantModel restaurantModel = new RestaurantModel(id, "Pobre Juan", "Diversos", 20,
                LocalDateTime.of(2024,10,10, 10,0,0),
                new AddressModel(), new HashSet<>());
        assertThat(restaurantModel).isNotNull();
        assertThat(restaurantModel.getId()).isEqualTo(id);
        assertThat(restaurantModel.getName()).isEqualTo("Pobre Juan");
        assertThat(restaurantModel.getTypeOfCuisine()).isEqualTo("Diversos");
        assertThat(restaurantModel.getCapacity()).isEqualTo(20);
        assertThat(restaurantModel.getDateCreate()).isEqualTo(LocalDateTime.of(2024,10,10, 10,0,0));
        assertThat(restaurantModel.getAddress()).isNotNull().isInstanceOf(AddressModel.class);
        assertThat(restaurantModel.getOpeningHours()).isNotNull();
    }

}
