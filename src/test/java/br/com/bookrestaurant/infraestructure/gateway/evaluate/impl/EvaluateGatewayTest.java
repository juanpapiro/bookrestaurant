package br.com.bookrestaurant.infraestructure.gateway.evaluate.impl;

import br.com.bookrestaurant.entity.evaluate.EvaluateEntity;
import br.com.bookrestaurant.infraestructure.gateway.impl.evaluate.EvaluateGateway;
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

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

public class EvaluateGatewayTest {

    @InjectMocks
    private EvaluateGateway evaluateGateway;

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
    class RegisterEvaluate {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldRegisterAddress() {
            EvaluateEntity evaluateEntityRegister = Util.buildEvaluateEntity();
            evaluateEntityRegister.setEvaluateId(Util.getUUID());
            when(evaluateGateway.registryEvaluate(Mockito.any(EvaluateEntity.class)))
                    .thenReturn(evaluateEntityRegister);
            EvaluateEntity evaluateEntity = evaluateGateway.registryEvaluate(Util.buildEvaluateEntity());
            assertThat(evaluateEntity).isNotNull().isInstanceOf(EvaluateEntity.class);
            assertThat(evaluateEntity.getEvaluateId()).isNotNull().isInstanceOf(UUID.class);
            verify(dataBase, times(1))
                    .registryEvaluate(Mockito.any(EvaluateEntity.class));
        }
    }

}
