package br.com.bookrestaurant.external.api;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.entity.reserve.exception.ReserveNotFoundException;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.controller.ReserveController;
import br.com.bookrestaurant.infraestructure.controller.ReserveController;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ClientRecord;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ReserveRecord;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ClientRecord;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ReserveRecord;
import br.com.bookrestaurant.utilsbytests.Util;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class ReserveApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DataBaseJpa dataBase;

    @Mock
    private ReserveController reserveController;

    private AutoCloseable mocks;

    private static final String ENDPOINT = "/reserve";

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        ReserveApiController restaurantApiController = new ReserveApiController(
                dataBase, reserveController);
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
        @Severity(SeverityLevel.BLOCKER)
        void testShoudRegisterReserve() throws Exception {
            ReserveEntity reserveEntity = Util.buildReserveEntitySaved();
            reserveEntity.setId(Util.getUUID());
            when(reserveController.register(Mockito.any(ReserveRecord.class), Mockito.any(ClientRecord.class),
                    Mockito.any(IDataBase.class))).thenReturn(reserveEntity);
            mockMvc.perform(post(ENDPOINT)
                    .content(Util.toJson(Util.buildReserveDtoRequest()))
                    .contentType(MediaType.APPLICATION_JSON)
            ).andExpect(status().isCreated());
            verify(reserveController, times(1))
                    .register(Mockito.any(ReserveRecord.class), Mockito.any(ClientRecord.class),
                            Mockito.any(IDataBase.class));
        }

        @Test
        @Severity(SeverityLevel.CRITICAL)
        void testShoudRegisterReserveBadRequest() throws Exception {
            when(reserveController.register(Mockito.any(ReserveRecord.class), Mockito.any(ClientRecord.class),
                    Mockito.any(IDataBase.class)))
                    .thenThrow(new ReserveInvalidException("Nome do Cliente é obrigatório"));
            assertThatThrownBy(() ->
                    mockMvc.perform(post(ENDPOINT)
                            .content(Util.toJson(Util.buildReserveDtoRequest()))
                            .contentType(MediaType.APPLICATION_JSON))
            ).isInstanceOf(Exception.class)
                    .hasMessageContaining("Nome do Cliente é obrigatório");
        }
    }

}
