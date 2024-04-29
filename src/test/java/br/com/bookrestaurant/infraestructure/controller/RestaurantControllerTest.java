package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantNotFoundException;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IDataBase;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.Mockito.when;

class RestaurantControllerTest {

    @InjectMocks
    private RestaurantController controller;

    @Mock
    private IDataBase database;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach()
    void closeMocks() throws Exception {
        mocks.close();
    }

    @Test
    void testRegisterRestaurant() {
        when(database.registerRestaurant(Mockito.any(RestaurantEntity.class)))
                .thenReturn(Util.buildRestaurantEntitySaved());
        RestaurantEntity restaurantEntity = controller.register(
                Util.buildRestaurantRecord(), Util.buildAddressRecord(),
                Util.buildOpeningHoursRecords(), database);
        assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
        assertThat(restaurantEntity.getId()).isNotNull().isEqualTo(Util.getUUID());
    }

    @Test
    void testRegisterRestaurantWhenException() {
        AddressRecord addressRecord = new AddressRecord(null, null, null, null, null, null);
        assertThatThrownBy(() -> controller.register(
                    Util.buildRestaurantRecord(), addressRecord,
                    Util.buildOpeningHoursRecords(), database))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Nome da rua é obrigatório");
    }


    @Nested
    class FindRestaurant {
        @Test
        void testShouldPermitFindRestaurantByName() {
            String name = "Restaurante da Mama";
            when(database.findByName(Mockito.anyString()))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName(name)));
            List<RestaurantEntity> restaurants = controller.findByName(name, database);
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
            assertThatThrownBy(() -> controller.findByName(name, database))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Nome inválido!");
        }
        @Test
        void testShouldThrowExceptionWhenFindRestaurantByNameWithEmpty() {
            String name = "Restaurante da Mama";
            when(database.findByName(Mockito.anyString())).thenReturn(Arrays.asList());
            assertThatThrownBy(() -> controller.findByName(name, database))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }
        @Test
        void testShouldThrowExceptionWhenFindRestaurantByNameWithNull() {
            String name = "Restaurante da Mama";
            when(database.findByName(Mockito.anyString())).thenReturn(null);
            assertThatThrownBy(() -> controller.findByName(name, database))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousine() {
            String typeOfCuisine = "Italiana";
            when(database.findByTypeOfCuisine(Mockito.anyString()))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName("Cusina")));
            List<RestaurantEntity> restaurants = controller.findByTypeOfCuisine(typeOfCuisine, database);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("typeOfCuisine")
                    .asString()
                    .isEqualToIgnoringCase(typeOfCuisine);
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousineNotValidParam() {
            String typeOfCuisine = "Ita";
            assertThatThrownBy(() -> controller.findByTypeOfCuisine(typeOfCuisine, database))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("Tipo de cozinha inválido!");
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousineListEmpty() {
            String typeOfCuisine = "Ita";
            when(database.findByTypeOfCuisine(Mockito.anyString())).thenReturn(Arrays.asList());
            assertThatThrownBy(() -> controller.findByName(typeOfCuisine, database))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousineListNull() {
            String typeOfCuisine = "Ita";
            when(database.findByTypeOfCuisine(Mockito.anyString())).thenReturn(null);
            assertThatThrownBy(() -> controller.findByName(typeOfCuisine, database))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }
        @Test
        void testShouldPermitFindRestaurantByLocation() {
            String uf = "SP";
            String city = "Embu das Artes";
            String neighborhood = "Jardim São Vicente";
            when(database.findByLocale(Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName("Cusina")));
            List<RestaurantEntity> restaurants = controller.findByLocale(uf, city, neighborhood, database);
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
            assertThatThrownBy(() -> controller.findByLocale(null, null, null, database))
                    .isInstanceOf(RestaurantInvalidException.class)
                    .hasMessage("UF ou cidade ou bairro deve ser informado.");
        }
        @Test
        void testShouldPermitFindRestaurantByLocationNotFound() {
            String uf = "SP";
            String city = "Embu das Artes";
            String neighborhood = "Jardim São Vicente";
            when(database.findByLocale(Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
                    .thenReturn(Arrays.asList());
            assertThatThrownBy(() -> controller.findByLocale(uf, city, neighborhood, database))
                    .isInstanceOf(RestaurantNotFoundException.class)
                    .hasMessage("Restaurante não localizado!");
        }
    }


}
