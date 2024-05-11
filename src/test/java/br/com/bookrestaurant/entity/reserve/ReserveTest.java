package br.com.bookrestaurant.entity.reserve;

import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ReserveTest {

    @Test
    void testShouldPermitBuildReserveEntity() {
        ReserveEntity reserveEntity = Util.buildReserve();
        assertThat(reserveEntity).isNotNull().isInstanceOf(ReserveEntity.class);
        assertThat(reserveEntity.getRestaurantId()).isNotNull().isEqualTo(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"));
        assertThat(reserveEntity.getDate()).isNotNull().isInstanceOf(LocalDateTime.class);
        assertThat(reserveEntity.getSeats()).isNotNull().isEqualTo(4);
    }

    @Test
    void testShouldExceptionWhenRegisterReserveEntityWithRestaurantIdNull() {
        assertThatThrownBy(() -> new ReserveEntity(null, LocalDateTime.now(), 4, "A"))
                .isInstanceOf(ReserveInvalidException.class)
                .hasMessage("Identificação do restaurante é obrigatória");

        assertThatThrownBy(() -> new ReserveEntity(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                null, 4, "A"))
                .isInstanceOf(ReserveInvalidException.class)
                .hasMessage("Data e hora da reserva é obrigatória");

        assertThatThrownBy(() -> new ReserveEntity(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                LocalDateTime.now(), null, "A"))
                .isInstanceOf(ReserveInvalidException.class)
                .hasMessage("Número de pessoas é obrigatório");

        assertThatThrownBy(() -> new ReserveEntity(UUID.fromString("ba94f4d5-b0a6-4745-adac-1456619ecca8"),
                LocalDateTime.now(), 4, null))
                .isInstanceOf(ReserveInvalidException.class)
                .hasMessage("Situação da reserva é obrigatória");
    }
}
