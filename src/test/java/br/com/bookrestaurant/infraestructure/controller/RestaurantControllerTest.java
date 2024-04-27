package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IDataBase;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
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


}
