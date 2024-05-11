package br.com.bookrestaurant.infraestructure.gateway.reserve.impl;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.infraestructure.gateway.impl.reserve.ReserveGateway;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ReserveGatewayTest {
    @InjectMocks
    private ReserveGateway reserveGateway;
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
    class RegisterReserve {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldRegisterClient() {
            reserveGateway.registerReserve(Util.buildReserveEntity());
            verify(dataBase, times(1))
                    .registerReserve(Mockito.any(ReserveEntity.class));
        }
    }

}
