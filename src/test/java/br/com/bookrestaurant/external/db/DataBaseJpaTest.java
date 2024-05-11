package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.model.*;
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
import java.util.Optional;
import java.util.UUID;

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

    @Mock
    private EvaluateRepository evaluateRepository;

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ReserveRepository reserveRepository;

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
        @Severity(SeverityLevel.BLOCKER)
        void shouldRegisterAddress() {
            dataBase.registerAddress(Util.buildAddres(), Util.getUUID());
            verify(addressRepository, times(1))
                    .save(Mockito.any(AddressModel.class));
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void shouldRegisterOpeningHour() {
            dataBase.registerOpeningHour(Util.buildOpeningHour(), Util.getUUID());
            verify(openingHourRepository, times(1))
                    .save(Mockito.any(OpeningHourModel.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        void shouldRegisterOpeningHours() {
            dataBase.registerOpeningHours(Util.buildOpeningHours(), Util.getUUID());
            verify(openingHourRepository, times(1)).saveAll(Mockito.anyList());
        }
    }


    @Nested
    class FindRestaurant {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitFindRestaurantByName() {
            String name = "Restaurante da Mama";
            when(restaurantRepository.findByName(Mockito.anyString()))
                    .thenReturn(Arrays.asList(Util.buildRestaurantModelForName(name)));
            List<RestaurantEntity> restaurants = dataBase.findRestaurantByName(name);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("name")
                    .asString()
                    .isEqualToIgnoringCase(name);
        }
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitFindRestaurantByTypeCousine() {
            String typeOfCuisine = "Francesa";
            RestaurantModel restaurantModel = Util.buildRestaurantModelForName("Cusina");
            restaurantModel.setTypeOfCuisine(typeOfCuisine);
            when(restaurantRepository.findByTypeOfCuisine(Mockito.anyString()))
                    .thenReturn(Arrays.asList(restaurantModel));
            List<RestaurantEntity> restaurants = dataBase.findRestaurantByTypeOfCuisine(typeOfCuisine);
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
            String uf = "sp";
            String city = "embu das artes";
            String neighborhood = "jardim são vicente";
            RestaurantModel restaurantModel = Util.buildRestaurantModelForName("Cusina");
            when(restaurantRepository.findByLocale(Mockito.anyString(),Mockito.anyString(),Mockito.anyString()))
                    .thenReturn(Arrays.asList(restaurantModel));
            List<RestaurantEntity> restaurants = dataBase.findRestaurantByLocale(uf, city, neighborhood);
            assertThat(restaurants).isNotNull().asList()
                    .element(0)
                    .isInstanceOf(RestaurantEntity.class)
                    .extracting("address")
                    .extracting("uf")
                    .asString()
                    .isEqualToIgnoringCase(uf);
        }
        @Test
        @Severity(SeverityLevel.TRIVIAL)
        void testShouldPermitFindRestaurantById() {
            RestaurantModel restaurantModel = Util.buildRestaurantModelForName("Cusina");
            when(restaurantRepository.findById(Mockito.any(UUID.class)))
                    .thenReturn(Optional.of(restaurantModel));
            RestaurantEntity restaurant = dataBase.findRestaurantById(Util.getUUID());
            assertThat(restaurant).isNotNull().isInstanceOf(RestaurantEntity.class);
        }
        @Test
        @Severity(SeverityLevel.TRIVIAL)
        void testShouldPermitFindRestaurantByIdReturnNull() {
            when(restaurantRepository.findById(Mockito.any(UUID.class)))
                    .thenReturn(Optional.empty());
            RestaurantEntity restaurant = dataBase.findRestaurantById(Util.getUUID());
            assertThat(restaurant).isNull();
        }
    }

    @Nested
    class RegisterEvaluate {
        @Test
        void testShouldRegisterEvaluate() {
            when(evaluateRepository.save(Mockito.any(EvaluateModel.class)))
                    .thenReturn(Util.buildEvaluateModel());
            EvaluateEntity evaluateEntity = dataBase.registryEvaluate(Util.buildEvaluateEntity());
            assertThat(evaluateEntity).isNotNull().isInstanceOf(EvaluateEntity.class);
            assertThat(evaluateEntity.getEvaluateId()).isNotNull().isInstanceOf(UUID.class);
        }
    }

    @Nested
    class RegisterReserve {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldRegisterReserve() {
            ReserveModel reserveModel = new ReserveModel(Util.buildReserveEntitySaved());
            reserveModel.setId(Util.getUUID());
            when(reserveRepository.save(Mockito.any(ReserveModel.class)))
                    .thenReturn(reserveModel);
            ReserveEntity reserveEntity = dataBase
                    .registerReserve(Util.buildReserveEntity());
            verify(reserveRepository, times(1))
                    .save(Mockito.any(ReserveModel.class));
            assertThat(reserveEntity).isNotNull().isInstanceOf(ReserveEntity.class);
            assertThat(reserveEntity.getId()).isNotNull().isEqualTo(Util.getUUID());
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        void shouldRegisterClient() {
            dataBase.registerClient(Util.buildClient(), Util.getUUID());
            verify(clientRepository, times(1))
                    .save(Mockito.any(ClientModel.class));
        }
    }

}
