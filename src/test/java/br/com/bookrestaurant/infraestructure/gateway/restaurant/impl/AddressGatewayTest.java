package br.com.bookrestaurant.infraestructure.gateway.restaurant.impl;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.AddressGateway;
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

public class AddressGatewayTest {

    @InjectMocks
    private AddressGateway addressGateway;

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
    class RegisterRestaurant {
        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldRegisterAddress() {
            addressGateway.registerAddress(Util.buildAddres(), Util.getUUID());
            verify(dataBase, times(1))
                    .registerAddress(Mockito.any(Address.class),
                            Mockito.any(UUID.class));
        }
    }

}
