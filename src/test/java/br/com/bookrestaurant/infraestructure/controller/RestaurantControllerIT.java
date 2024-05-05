package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantNotFoundException;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import br.com.bookrestaurant.utilsbytests.Util;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.Mockito.when;

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


    @Nested
    class RegisterRestaurant {
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
            AddressRecord addressRecord = new AddressRecord(null, null,
                    null, null, null, null);
            assertThatThrownBy(() -> controller.register(
                    Util.buildRestaurantRecord(), addressRecord,
                    Util.buildOpeningHoursRecords(), dataBaseJpa))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Nome da rua é obrigatório");
        }
    }

    @Nested
    class FindRestaurant {
        @Test
        void testShouldPermitFindRestaurantByName() {
            String name = "Restaurante da Mama";
            List<RestaurantEntity> restaurants = controller.findByName(name, dataBaseJpa);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("name")
                    .asString()
                    .isEqualToIgnoringCase(name);
        }
        @Test
        void testShouldThrowExceptionWhenFindRestaurantByNameWithNameNotValid() {
            String name = "R";
            assertThatThrownBy(() -> controller.findByName(name, dataBaseJpa))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Nome inválido!");
        }
        @Test
        void testShouldThrowExceptionWhenFindRestaurantByNameWithEmpty() {
            String name = "El Guatón";
            assertThatThrownBy(() -> controller.findByName(name, dataBaseJpa))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousine() {
            String typeOfCuisine = "Italiana";
            List<RestaurantEntity> restaurants = controller.findByTypeOfCuisine(typeOfCuisine, dataBaseJpa);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("typeOfCuisine")
                    .asString()
                    .isEqualToIgnoringCase(typeOfCuisine);
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousineParamNotValid() {
            String typeOfCuisine = "Ita";
            assertThatThrownBy(() -> controller.findByTypeOfCuisine(typeOfCuisine, dataBaseJpa))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Tipo de cozinha inválido!");
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousineListEmpty() {
            String typeOfCuisine = "Portuguesa";
            assertThatThrownBy(() -> controller.findByTypeOfCuisine(typeOfCuisine, dataBaseJpa))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }
        @Test
        void testShouldPermitFindRestaurantByLocation() {
            String uf = "SP";
            String city = "Embu das Artes";
            String neighborhood = "Jardim São Vicente";
            List<RestaurantEntity> restaurants = controller.findByLocale(uf, city, neighborhood, dataBaseJpa);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("address")
                    .extracting("uf")
                    .asString()
                    .isEqualToIgnoringCase(uf);
        }

        @Test
        void testShouldPermitFindRestaurantByLocationBadRequest() {
            assertThatThrownBy(() -> controller.findByLocale(null, null, null, dataBaseJpa))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("UF ou cidade ou bairro deve ser informado.");
        }
        @Test
        void testShouldPermitFindRestaurantByLocationNotFound() {
            String uf = "RJ";
            String city = "Embu das Artes";
            String neighborhood = "Jardim São Vicente";
            assertThatThrownBy(() -> controller.findByLocale(uf, city, neighborhood, dataBaseJpa))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }
    }

}
