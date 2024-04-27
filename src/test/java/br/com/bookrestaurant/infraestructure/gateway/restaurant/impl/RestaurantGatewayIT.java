package br.com.bookrestaurant.infraestructure.gateway.restaurant.impl;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.RestaurantGateway;
import br.com.bookrestaurant.utilsbytests.Util;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureTestDatabase
@Transactional
class RestaurantGatewayIT {

    private RestaurantGateway restaurantGateway;

    @Autowired
    private DataBaseJpa dataBaseJpa;

    @BeforeEach
    void setup() {
        restaurantGateway = new RestaurantGateway(dataBaseJpa);
    }

    @Test
    void testShourRegisterRestaurant() {
        RestaurantEntity restaurantEntity = restaurantGateway
                .registerRestaurant(Util.buildRestaurantEntity());
        assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
        assertThat(restaurantEntity.getId()).isNotNull().isInstanceOf(UUID.class);
    }

}
