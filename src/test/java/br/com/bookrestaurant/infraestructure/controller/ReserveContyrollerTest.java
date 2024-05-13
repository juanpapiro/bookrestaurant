package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ClientRecord;
import br.com.bookrestaurant.utilsbytests.Util;

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
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.*;

class ReserveContyrollerTest {
    @InjectMocks
    private ReserveController reserveController;
    @Mock
    private IDataBase database;

    private AutoCloseable mocks;

    @BeforeEach
    void setup() {
        mocks = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    void closeMocks() throws Exception {
        mocks.close();
    }

    @Nested
    class RegisterReserve {
        @Test
        void testRegisterReserve() {
            when(database.registerReserve(Mockito.any(ReserveEntity.class)))
                    .thenReturn(Util.buildReserveEntitySaved());
            ReserveEntity reserveEntity = reserveController.register(
                    Util.buildReserveRecord(), Util.buildClientRecord(),
                    database);
            assertThat(reserveEntity).isNotNull().isInstanceOf(ReserveEntity.class);
            assertThat(reserveEntity.getId()).isNotNull().isEqualTo(Util.getUUID());
        }

        @Test
        void testRegisterReserveWhenException() {
            ClientRecord clientRecord = new ClientRecord(null, null);
            assertThatThrownBy(() -> reserveController.register(
                    Util.buildReserveRecord(), clientRecord,
                    database))
                    .isInstanceOf(ReserveInvalidException.class)
                    .hasMessage("Nome é obrigatório");
        }
    }

    @Nested
    class FindReserve {
        @Test
        void testFindReserveByRestaurantAndDate() {
            LocalDateTime date = LocalDateTime.of(2024,5,13,11,24,59);
            when(database.findReserveByRestaurantAndDate(Mockito.any(UUID.class),
                    Mockito.any(LocalDateTime.class)))
                    .thenReturn(Arrays.asList(Util.buildReserveEntitySaved()));
            List<ReserveEntity> reserves = reserveController.findByRestaurantAndDate(Util.getUUID(), date, database);
            assertThat(reserves).isNotNull().isInstanceOf(List.class);
        }
    }

    @Nested
    class UpdateReserve {
        @Test
        void testUpdateStatus() {
            reserveController.updateStatus(Util.getUUID(), "F", database);
            verify(database, times(1)).updateStatus(Mockito.any(UUID.class), Mockito.anyString());
        }
    }

}
