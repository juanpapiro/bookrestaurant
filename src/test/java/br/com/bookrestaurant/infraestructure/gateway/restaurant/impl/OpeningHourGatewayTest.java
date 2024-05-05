package br.com.bookrestaurant.infraestructure.gateway.restaurant.impl;

import br.com.bookrestaurant.entity.restaurant.OpeningHour;
import br.com.bookrestaurant.infraestructure.gateway.impl.restaurant.OpeningHourGateway;
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

class OpeningHourGatewayTest {

    @InjectMocks
    private OpeningHourGateway openingHourGateway;

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
        @Severity(SeverityLevel.MINOR)
        void testShouldRegisterOpeningHour() {
            openingHourGateway.registerOpeningHour(
                    Util.buildOpeningHour(), Util.getUUID());
            verify(dataBase, times(1))
                    .registerOpeningHour(Mockito.any(OpeningHour.class),
                            Mockito.any(UUID.class));
        }

        @Test
        @Severity(SeverityLevel.BLOCKER)
        void testShouldRegisterOpeningHours() {
            openingHourGateway.registerOpeningHours(
                    Util.buildOpeningHours(), Util.getUUID());
            verify(dataBase, times(1))
                    .registerOpeningHours(Mockito.anyList(),
                            Mockito.any(UUID.class));
        }
    }

}
