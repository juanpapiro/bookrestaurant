package br.com.bookrestaurant.infraestructure.gateway.restaurant.impl;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.RestaurantGateway;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.utilsbytests.Util;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.Mockito.*;

public class RestaurantGatewayTest {

    @InjectMocks
    private RestaurantGateway restaurantGateway;

    @Mock
    private IDataBase dataBase;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void close() throws Exception {
        mocks.close();
    }


    @Nested
    class RegisterRestaurant {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShourRegisterRestaurant() {
            restaurantGateway.registerRestaurant(Util.buildRestaurantEntity());
            verify(dataBase, times(1))
                    .registerRestaurant(Mockito.any(RestaurantEntity.class));
        }
    }

    @Nested
    class FindRestaurant {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitFindRestaurantByName() {
            String name = "restaurante da mama";
            when(dataBase.findRestaurantByName(Mockito.anyString()))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName(name)));
            List<RestaurantEntity> restaurants = restaurantGateway.findRestaurantByName(name);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("name").isEqualTo(name);
        }
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitFindRestaurantByTypeCousine() {
            String typeOfCuisine = "Italiana";
            when(dataBase.findRestaurantByTypeOfCuisine(Mockito.anyString()))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName("Cusina")));
            List<RestaurantEntity> restaurants = restaurantGateway.findRestaurantByTypeOfCuisine(typeOfCuisine);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("typeOfCuisine")
                    .asString()
                    .isEqualToIgnoringCase(typeOfCuisine);
        }
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitFindRestaurantByLocation() {
            String uf = "SP";
            String city = "Embu das Artes";
            String neighborhood = "Jardim São Vicente";
            when(dataBase.findRestaurantByLocale(Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName("Cusina")));
            List<RestaurantEntity> restaurants = restaurantGateway.findRestaurantByLocale(uf, city, neighborhood);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("address")
                    .extracting("uf")
                    .asString()
                    .isEqualToIgnoringCase(uf);
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldFindRestaurantById() {
            when(dataBase.findRestaurantById(Mockito.any(UUID.class)))
                    .thenReturn(Util.buildRestaurantEntitySaved());
            RestaurantEntity restaurantEntity = restaurantGateway.findRestaurantById(Util.getUUID());
            assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
            assertThat(restaurantEntity.getId()).isEqualTo(Util.getUUID());
        }
    }


}
