package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class RestaurantEntityTest {

    @Test
    void testShouldPermitRegisterRestaurantEntity() {
        RestaurantEntity restaurantEntity = Util.buildRestaurantEntity();
        assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
        assertThat(restaurantEntity.getName()).isNotNull().isEqualTo("Nome do restaurante");
        assertThat(restaurantEntity.getTypeOfCusine()).isNotNull().isEqualTo("Italiana");
        assertThat(restaurantEntity.getCapacity()).isNotNull().isEqualTo(20);
        assertThat(restaurantEntity.getDateCreate()).isNotNull().isInstanceOf(LocalDateTime.class);
//        assertThat(restaurantEntity.getAddress()).isNotNull().isInstanceOf(Address.class);
//        assertThat(restaurantEntity.getOpeningHours()).isNotNull()
//                .asList().isNotEmpty().element(0).isInstanceOf(OpeningHours.class);
    }

    @Test
    void testShouldThowExceptionWhenRegisterRestaurantEntityWithNameNull() {
        assertThatThrownBy(() -> new RestaurantEntity(null, "Italiana", 20))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Nome é obrigatório");

        assertThatThrownBy(() -> new RestaurantEntity("Nome do restaurante", " ", 20))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Tipo de culinária é obrigatório");

        assertThatThrownBy(() -> new RestaurantEntity("Nome do restaurante", "Italiana", null))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Capacidade é obrigatória");
    }



}
