package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class RestaurantEntityBuilderTest {

    @Test
    void testShouldBuildRestaurantEntityWithInfos() {
        RestaurantEntity restaurantEntity = buildWithInfos().build();
        assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
        assertThat(restaurantEntity.getAddress()).isNull();
        assertThat(restaurantEntity.getOpeningHours()).isNull();
    }

    @Test
    void testShouldBuildRestaurantEntityWithInfo() {
        RestaurantEntity restaurantEntity = buildWithInfos()
                .addOpeningHours(Util.buildOpeningHours())
                .build();
        assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
        assertThat(restaurantEntity.getAddress()).isNull();
        assertThat(restaurantEntity.getOpeningHours()).isNotNull()
                .asList().isNotEmpty()
                .element(0).isNotNull().isInstanceOf(OpeningHour.class);
    }

    @Test
    void testShouldThrowExceptionWhenBuildRestaurantEntityNeverInfos() {
        assertThatThrownBy(() -> new RestaurantEntityBuilder()
                .addAddress(Util.buildAddres()).build())
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Falha ao criar novo restaurante");

        assertThatThrownBy(() -> new RestaurantEntityBuilder()
                .addOpeningHours(Util.buildOpeningHours()).build())
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Falha ao criar novo restaurante");
    }

    private RestaurantEntityBuilder buildWithInfos() {
        return new RestaurantEntityBuilder().addInfos("Nome restaurante",
                "Italiana", 20);
    }

}
