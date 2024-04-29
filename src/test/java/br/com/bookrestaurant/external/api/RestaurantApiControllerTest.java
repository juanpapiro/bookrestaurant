package br.com.bookrestaurant.external.api;

import br.com.bookrestaurant.entity.restaurant.RestaurantEntity;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.entity.restaurant.exception.RestaurantNotFoundException;
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

import java.util.Arrays;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.fail;
import static org.hamcrest.Matchers.not;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class RestaurantApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DataBaseJpa dataBase;

    @Mock
    private RestaurantController restaurantController;

    private AutoCloseable mocks;

    private static final String ENDPOINT = "/restaurant";
    private static final String ENDPOINT_TYPE_OF_CUISINE = "/by-type-of-cuisine";

    private static final String ENDPOINT_LOCALE = "/by-locale";

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


    @Nested
    class FindRestaurant {
        @Test
        void testShouldPermitFindRestaurantByName() throws Exception {
            String name = "Restaurante da Mama";
            when(restaurantController.findByName(Mockito.anyString(), Mockito.any(IDataBase.class)))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName(name)));
            mockMvc.perform(get(ENDPOINT + "/by-name").queryParam("name", name))
                    .andExpect(status().isOk());
            verify(restaurantController, times(1))
                    .findByName(Mockito.anyString(), Mockito.any(IDataBase.class));
        }

        @Test
        void testShouldFindRestaurantByBadRequest() throws Exception {
            String name = "R";
            when(restaurantController.findByName(Mockito.anyString(), Mockito.any(IDataBase.class)))
                    .thenThrow(new RestaurantInvalidException("Nome inválido!"));
            assertThatThrownBy(() -> {
                mockMvc.perform(get(ENDPOINT + "/by-name").queryParam("name", name))
                        .andExpect(status().isBadRequest());
            }).hasMessageContaining("Nome inválido!");
            verify(restaurantController, times(1))
                    .findByName(Mockito.anyString(), Mockito.any(IDataBase.class));
        }

        @Test
        void testShouldFindRestaurantByNotFound() throws Exception {
            String name = "Restaurante da Mama";
            when(restaurantController.findByName(Mockito.anyString(), Mockito.any(IDataBase.class)))
                    .thenThrow(new RestaurantNotFoundException());
            assertThatThrownBy(() -> {
                mockMvc.perform(get(ENDPOINT + "/by-name").queryParam("name", name))
                        .andExpect(status().isBadRequest());
            }).hasMessageContaining("não localizado");
            verify(restaurantController, times(1))
                    .findByName(Mockito.anyString(), Mockito.any(IDataBase.class));
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousine() throws Exception {
            String typeOfCuisine = "Italiana";
            when(restaurantController.findByTypeOfCuisine(Mockito.anyString(), Mockito.any(IDataBase.class)))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName("Cuisine")));
            mockMvc.perform(get(ENDPOINT + ENDPOINT_TYPE_OF_CUISINE)
                            .queryParam("typeOfCuisine", typeOfCuisine)
                    ).andExpect(status().isOk());
            verify(restaurantController, times(1))
                    .findByTypeOfCuisine(Mockito.anyString(), Mockito.any(IDataBase.class));
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousineBadRequest() throws Exception {
            String typeOfCuisine = "Ita";
            when(restaurantController.findByTypeOfCuisine(Mockito.anyString(), Mockito.any(IDataBase.class)))
                    .thenThrow(new RestaurantInvalidException("Tipo de cozinha inválido!"));
            assertThatThrownBy(() -> {
                mockMvc.perform(get(ENDPOINT + ENDPOINT_TYPE_OF_CUISINE)
                        .queryParam("typeOfCuisine", typeOfCuisine));
            }).hasMessageContaining("Tipo de cozinha inválido!");
            verify(restaurantController, times(1))
                    .findByTypeOfCuisine(Mockito.anyString(), Mockito.any(IDataBase.class));
        }
        @Test
        void testShouldPermitFindRestaurantByTypeCousineNotFound() throws Exception {
            String typeOfCuisine = "Italiana";
            when(restaurantController.findByTypeOfCuisine(Mockito.anyString(), Mockito.any(IDataBase.class)))
                    .thenThrow(new RestaurantNotFoundException());
            assertThatThrownBy(() -> {
                mockMvc.perform(get(ENDPOINT + ENDPOINT_TYPE_OF_CUISINE)
                        .queryParam("typeOfCuisine", typeOfCuisine));
            }).hasMessageContaining("não localizado");
            verify(restaurantController, times(1))
                    .findByTypeOfCuisine(Mockito.anyString(), Mockito.any(IDataBase.class));
        }
        @Test
        void testShouldPermitFindRestaurantByLocation() throws Exception {
            String uf = "SP";
            String city = "Embu das Artes";
            String neighborhood = "Jardim São Vicente";
            when(restaurantController.findByLocale(Mockito.anyString(), Mockito.anyString(),
                    Mockito.anyString(), Mockito.any(IDataBase.class)))
                    .thenReturn(Arrays.asList(Util.buildRestaurantEntityForName("Cusina")));
            mockMvc.perform(get(ENDPOINT + ENDPOINT_LOCALE)
                            .queryParam("uf", uf)
                            .queryParam("city", city)
                            .queryParam("neighborhood", neighborhood))
                    .andExpect(status().isOk());
            verify(restaurantController, times(1))
                    .findByLocale(Mockito.anyString(), Mockito.anyString(),
                            Mockito.anyString(), Mockito.any(IDataBase.class));
        }

        @Test
        void testShouldPermitFindRestaurantByLocationBadRequest() throws Exception {
            when(restaurantController.findByLocale(Mockito.any(), Mockito.any(),
                    Mockito.any(), Mockito.any(IDataBase.class)))
                    .thenThrow(new RestaurantInvalidException("UF ou cidade ou bairro deve ser informado."));
            assertThatThrownBy(() ->
                mockMvc.perform(get(ENDPOINT + ENDPOINT_LOCALE))
            ).hasMessageContaining("UF ou cidade ou bairro deve ser informado.");
            verify(restaurantController, times(1))
                    .findByLocale(Mockito.any(), Mockito.any(), Mockito.any(), Mockito.any(IDataBase.class));
        }

        @Test
        void testShouldPermitFindRestaurantByLocationNotFound() throws Exception {
            String uf = "RJ";
            when(restaurantController.findByLocale(Mockito.anyString(), Mockito.any(),
                    Mockito.any(), Mockito.any(IDataBase.class)))
                    .thenThrow(new RestaurantNotFoundException());
            assertThatThrownBy(() ->
                mockMvc.perform(get(ENDPOINT + ENDPOINT_LOCALE).queryParam("uf", uf))
            ).hasMessageContaining("não localizado");
            verify(restaurantController, times(1))
                    .findByLocale(Mockito.anyString(), Mockito.any(), Mockito.any(), Mockito.any(IDataBase.class));
        }
    }


}
