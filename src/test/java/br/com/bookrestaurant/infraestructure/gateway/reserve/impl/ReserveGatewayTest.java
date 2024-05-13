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

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

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

    @Nested
    class FindReserve {
        @Test
        void testFindReserveByRestaurantAndDate() {
            LocalDateTime date = LocalDateTime.of(2024,5,13,11,24,59);
            when(dataBase.findReserveByRestaurantAndDate(
                    Mockito.any(UUID.class), Mockito.any(LocalDateTime.class)))
                    .thenReturn(Arrays.asList(Util.buildReserveEntitySaved()));
            List<ReserveEntity> reserves = reserveGateway.findReserveByRestaurantAndDate(Util.getUUID(), date);
            assertThat(reserves).isNotNull().isInstanceOf(List.class);
        }
    }

    @Nested
    class UpdateReserve {
        @Test
        void testUpdateStatus() {
            reserveGateway.updateStatus(Util.getUUID(), "F");
            verify(dataBase, times(1)).updateStatus(Mockito.any(UUID.class), Mockito.anyString());
        }
    }

}
