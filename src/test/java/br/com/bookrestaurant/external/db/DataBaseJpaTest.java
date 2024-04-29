package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.model.AddressModel;
import br.com.bookrestaurant.external.model.OpeningHourModel;
import br.com.bookrestaurant.external.model.RestaurantModel;
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

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.mockito.Mockito.*;

class DataBaseJpaTest {

    @InjectMocks
    private DataBaseJpa dataBase;

    @Mock
    private OpeningHourRepository openingHourRepository;

    @Mock
    private AddressRepository addressRepository;

    @Mock
    private RestaurantRepository restaurantRepository;

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
        void testShouldRegisterRestaurant() {
            RestaurantModel restaurantModel = new RestaurantModel(Util.buildRestaurantEntitySaved());
            restaurantModel.setId(Util.getUUID());
            when(restaurantRepository.save(Mockito.any(RestaurantModel.class)))
                    .thenReturn(restaurantModel);
            RestaurantEntity restaurantEntity = dataBase
                    .registerRestaurant(Util.buildRestaurantEntity());
            verify(restaurantRepository, times(1))
                    .save(Mockito.any(RestaurantModel.class));
            assertThat(restaurantEntity).isNotNull().isInstanceOf(RestaurantEntity.class);
            assertThat(restaurantEntity.getId()).isNotNull().isEqualTo(Util.getUUID());
        }

        @Test
        void shouldRegisterAddress() {
            dataBase.registerAddress(Util.buildAddres(), Util.getUUID());
            verify(addressRepository, times(1))
                    .save(Mockito.any(AddressModel.class));
        }

        @Test
        void shouldRegisterOpeningHour() {
            dataBase.registerOpeningHour(Util.buildOpeningHour(), Util.getUUID());
            verify(openingHourRepository, times(1))
                    .save(Mockito.any(OpeningHourModel.class));
        }

        @Test
        void shouldRegisterOpeningHours() {
            dataBase.registerOpeningHours(Util.buildOpeningHours(), Util.getUUID());
            verify(openingHourRepository, times(1)).saveAll(Mockito.anyList());
        }
    }


    @Nested
    class FindRestaurant {
        @Test
        void testShouldPermitFindRestaurantByName() {
            String name = "Restaurante da Mama";
            when(restaurantRepository.findByName(Mockito.anyString()))
                    .thenReturn(Arrays.asList(Util.buildRestaurantModelForName(name)));
            List<RestaurantEntity> restaurants = dataBase.findByName(name);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("name")
                    .asString()
                    .isEqualToIgnoringCase(name);
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousine() {
            String typeOfCuisine = "Francesa";
            RestaurantModel restaurantModel = Util.buildRestaurantModelForName("Cusina");
            restaurantModel.setTypeOfCuisine(typeOfCuisine);
            when(restaurantRepository.findByTypeOfCuisine(Mockito.anyString()))
                    .thenReturn(Arrays.asList(restaurantModel));
            List<RestaurantEntity> restaurants = dataBase.findByTypeOfCuisine(typeOfCuisine);
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
            RestaurantModel restaurantModel = Util.buildRestaurantModelForName("Cusina");
            when(restaurantRepository.findByLocale(Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
                    .thenReturn(Arrays.asList(restaurantModel));
            List<RestaurantEntity> restaurants = dataBase.findByLocale(uf, city, neighborhood);
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
