package br.com.bookrestaurant.external.api;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.controller.RestaurantController;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.restaurant.IDataBase;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.AddressRecord;
import br.com.bookrestaurant.infraestructure.presenter.restaurant.RestaurantRecord;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DataBaseJpa dataBase;

    @Mock
    private RestaurantController restaurantController;

    private AutoCloseable mocks;

    private static final String ENDPOINT = "/restaurant";

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        RestaurantApiController restaurantApiController = new RestaurantApiController(
                dataBase, restaurantController);
        mockMvc = MockMvcBuilders.standaloneSetup(restaurantApiController)
                .addFilter((request, response, chain) -> {
                    response.setCharacterEncoding("UTF-8");
                    chain.doFilter(request, response);
                }).build();
    }

    @AfterEach
    void close() throws Exception {
        mocks.close();
    }

    @Nested
    class RegisterRestaurant {
        @Test
        void testShoudRegisterRestaurant() throws Exception {
            RestaurantEntity restaurantEntity = Util.buildRestaurantEntitySaved();
            restaurantEntity.setId(Util.getUUID());
            when(restaurantController.register(Mockito.any(RestaurantRecord.class), Mockito.any(AddressRecord.class),
                    Mockito.anyList(), Mockito.any(IDataBase.class))).thenReturn(restaurantEntity);
            mockMvc.perform(post(ENDPOINT)
                    .content(Util.toJson(Util.buildRestaurantDtoRequest()))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());
            verify(restaurantController, times(1))
                    .register(Mockito.any(RestaurantRecord.class), Mockito.any(AddressRecord.class),
                            Mockito.anyList(), Mockito.any(IDataBase.class));
        }

        @Test
        void testShoudRegisterRestaurantBadRequest() throws Exception {
            when(restaurantController.register(Mockito.any(RestaurantRecord.class), Mockito.any(AddressRecord.class),
                    Mockito.anyList(), Mockito.any(IDataBase.class)))
                    .thenThrow(new RestaurantInvalidException("Lista de horários de funcionamento deve conter no máximo 7 dias"));
            assertThatThrownBy(() ->
                    mockMvc.perform(post(ENDPOINT)
                            .content(Util.toJson(Util.buildRestaurantDtoRequest()))
                            .contentType(MediaType.APPLICATION_JSON))
            ).isInstanceOf(Exception.class)
                    .hasMessageContaining("Lista de horários de funcionamento deve conter no máximo 7 dias");
        }
    }


}
