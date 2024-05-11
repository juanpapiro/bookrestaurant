package br.com.bookrestaurant.entity.reserve;

import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReserveEntityBuilderTest {

    @Test
    void testShouldBuildReserveEntityWithInfo() {
        ReserveEntity reserveEntity = buildWithInfo().build();
        assertThat(reserveEntity).isNotNull().isInstanceOf(ReserveEntity.class);
        assertThat(reserveEntity.getClient()).isNull();
    }

    @Test
    void testShouldThrowExceptionWhenBuildReserveEntityWithoutInfos() {
        assertThatThrownBy(() -> new ReserveEntityBuilder()
                .addClient(Util.buildClient()).build())
                .isInstanceOf(ReserveInvalidException.class)
                .hasMessage("Falha ao criar nova reserva");
    }

    private ReserveEntityBuilder buildWithInfo() {
        return new ReserveEntityBuilder().addInfos( UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                LocalDateTime.now(), 4, "A");
    }
}
