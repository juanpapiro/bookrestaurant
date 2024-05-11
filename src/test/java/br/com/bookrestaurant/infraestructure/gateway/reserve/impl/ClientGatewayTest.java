package br.com.bookrestaurant.infraestructure.gateway.reserve.impl;

import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.infraestructure.gateway.impl.reserve.ClientGateway;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ClientGatewayTest {
    @InjectMocks
    private ClientGateway clientGateway;
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
            clientGateway.registerClient(Util.buildClient(), Util.getUUID());
            verify(dataBase, times(1))
                    .registerClient(Mockito.any(Client.class),
                            Mockito.any(UUID.class));
        }
    }

}
