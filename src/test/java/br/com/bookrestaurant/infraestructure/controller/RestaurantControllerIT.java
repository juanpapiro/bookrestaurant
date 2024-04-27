package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import br.com.bookrestaurant.utilsbytests.Util;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
public class RestaurantControllerIT {

    private RestaurantController controller;

    @Autowired
    private DataBaseJpa dataBaseJpa;

    @BeforeEach
    void setup() {
        controller = new RestaurantController();
    }


    @Test
    void testRegisterRestaurant() {
        RestaurantEntity restaurantEntity = controller.register(
                Util.buildRestaurantRecord(), Util.buildAddressRecord(),
                Util.buildOpeningHoursRecords(), dataBaseJpa);
        assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
        assertThat(restaurantEntity.getId()).isNotNull().isInstanceOf(UUID.class);
    }

    @Test
    void testRegisterRestaurantWhenException() {
        AddressRecord addressRecord = new AddressRecord(null, null, null, null, null, null);
        assertThatThrownBy(() -> controller.register(
                Util.buildRestaurantRecord(), addressRecord,
                Util.buildOpeningHoursRecords(), dataBaseJpa))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Nome da rua é obrigatório");
    }

}
