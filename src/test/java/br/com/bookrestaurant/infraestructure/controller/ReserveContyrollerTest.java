package br.com.bookrestaurant.infraestructure.controller;

import br.com.bookrestaurant.entity.reserve.ReserveEntity;
import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.infraestructure.gateway.interfaces.IDataBase;
import br.com.bookrestaurant.infraestructure.presenter.reserve.ClientRecord;
import br.com.bookrestaurant.utilsbytests.Util;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.Mockito.when;

public class ReserveContyrollerTest {
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
