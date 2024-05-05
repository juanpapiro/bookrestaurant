package br.com.bookrestaurant.external.api;


import br.com.bookrestaurant.entity.evaluate.exception.EvaluateInvalidException;
import br.com.bookrestaurant.external.db.DataBaseJpa;
import br.com.bookrestaurant.infraestructure.controller.EvaluateController;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.presenter.evaluate.EvaluateRecord;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class EvaluateApiControllerTest {

    private MockMvc mockMvc;

    @Mock
    private DataBaseJpa dataBaseJpa;

    @Mock
    private EvaluateController evaluateController;

    private AutoCloseable mocks;

    private static final String ENDPOINT = "/evaluate";

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
        EvaluateApiController evaluateApiController = new EvaluateApiController(dataBaseJpa, evaluateController);
        mockMvc = MockMvcBuilders.standaloneSetup(evaluateApiController)
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
    class RegisterEvaluate {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldPermitRegisterEvaluate() throws Exception {
            when(evaluateController.register(Mockito.any(EvaluateRecord.class),
                    Mockito.any(IDataBase.class))).thenReturn(Util.buildEvaluateEntitySaved());
            mockMvc.perform(post(ENDPOINT)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(Util.toJson(Util.buildEvaluateDto()))
            ).andExpect(status().isCreated());
            verify(evaluateController, times(1)).register(Mockito.any(EvaluateRecord.class),
                    Mockito.any(IDataBase.class));
        }

        @Test
        @Severity(SeverityLevel.MINOR)
        void testShouldNotPermitRegisterEvaluateWhenRestaurantNotFound() throws Exception {
            when(evaluateController.register(Mockito.any(EvaluateRecord.class), Mockito.any(IDataBase.class)))
                    .thenThrow(new EvaluateInvalidException("Restaurante avaliado inexistente"));
            assertThatThrownBy(() ->
                mockMvc.perform(post(ENDPOINT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(Util.toJson(Util.buildEvaluateDto()))
                )
            ).isInstanceOf(Exception.class)
                    .hasMessageContaining("Restaurante avaliado inexistente");

        }
    }

}
