package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.UUID;

import static org.mockito.Mockito.when;

class EvaluateControllerTest {

    @InjectMocks
    private EvaluateController evaluateController;

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

    @Nested
    class RegisterEvaluate {
        @Test
        void testShouldPermitRegisterEvaluate() {
            when(database.findRestaurantById(Mockito.any(UUID.class)))
                    .thenReturn(Util.buildRestaurantEntitySaved());
            when(database.registryEvaluate(Mockito.any(EvaluateEntity.class)))
                    .thenReturn(Util.buildEvaluateEntity());
            EvaluateEntity evaluateEntity = evaluateController
                    .register(Util.buildEvaluateRecord(), database);
        }
    }


}
