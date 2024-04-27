package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.utilsbytests.Util;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class DataBaseJpaIT {

    @Autowired
    private DataBaseJpa dataBaseJpa;

    @Test
    void testShouldPermitRegisterRestaurant() {
        RestaurantEntity restaurantEntity = dataBaseJpa
                .registerRestaurant(Util.buildRestaurantEntity());
        assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
        assertThat(restaurantEntity.getId()).isNotNull().isInstanceOf(UUID.class);
    }

    @Test
    void testShouldPermitRegisterAddress() {
        Address address = dataBaseJpa.registerAddress(Util.buildAddres(), Util.getUUID());
        assertThat(address).isNotNull().isInstanceOf(Address.class);
    }

    @Test
    void testShouldPermitRegisterOpeningHour() {
        OpeningHour openingHour = dataBaseJpa.registerOpeningHour(Util.buildOpeningHour(), Util.getUUID());
        assertThat(openingHour).isNotNull().isInstanceOf(OpeningHour.class);
    }

    @Test
    void testShouldPermitRegisterOpeningHours() {
        List<OpeningHour> openingHours = dataBaseJpa.registerOpeningHours(Util.buildOpeningHours(), Util.getUUID());
        assertThat(openingHours).isNotNull().asList().isNotEmpty()
                .element(0).isInstanceOf(OpeningHour.class);
    }

}
