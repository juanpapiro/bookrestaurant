package br.com.bookrestaurant.infraestructure.gateway.restaurant.impl;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.RestaurantGateway;
import br.com.bookrestaurant.utilsbytests.Util;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;


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


    @Nested
    class RegisterRestaurant {
        @Test
        void testShourRegisterRestaurant() {
            RestaurantEntity restaurantEntity = restaurantGateway
                    .registerRestaurant(Util.buildRestaurantEntity());
            assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
            assertThat(restaurantEntity.getId()).isNotNull().isInstanceOf(UUID.class);
        }
    }

    @Nested
    class FindRestaurant {
        @Test
        void testShouldPermitFindRestaurantByName() {
            String name = "restaurante da mama";
            List<RestaurantEntity> restaurants = restaurantGateway.findByName(name);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("name")
                    .asString()
                    .isEqualToIgnoringCase(name);
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousine() {
            String typeOfCuisine = "italiana";
            List<RestaurantEntity> restaurants = restaurantGateway.findByTypeOfCuisine(typeOfCuisine);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("typeOfCuisine")
                    .asString()
                    .isEqualToIgnoringCase(typeOfCuisine);
        }
        @Test
        void testShouldPermitFindRestaurantByLocation() {
            String uf = "sp";
            String city = "embu das artes";
            String neighborhood = "jardim são vicente";
            List<RestaurantEntity> restaurants = restaurantGateway.findByLocale(uf, city, neighborhood);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("address")
                    .extracting("uf")
                    .asString()
                    .isEqualToIgnoringCase(uf);
        }
    }

}
