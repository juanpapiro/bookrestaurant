package br.com.bookrestaurant.external.db;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.external.model.AddressModel;
import br.com.bookrestaurant.external.model.OpeningHourModel;
import br.com.bookrestaurant.external.model.RestaurantModel;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
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
