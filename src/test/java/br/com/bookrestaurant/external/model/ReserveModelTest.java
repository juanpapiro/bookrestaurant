package br.com.bookrestaurant.external.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ReserveModelTest {
    @Test
    void testInstance() {
        UUID id = UUID.randomUUID();
        UUID restaurantId = UUID.randomUUID();

        ReserveModel reserveModel = new ReserveModel(id, restaurantId,
                LocalDateTime.of(2024,10,10, 10,0,0),
                4, "A", new ClientModel());

        assertThat(reserveModel).isNotNull();
        assertThat(reserveModel.getId()).isEqualTo(id);
        assertThat(reserveModel.getDate()).isEqualTo(LocalDateTime.of(2024,10,10, 10,0,0));
        assertThat(reserveModel.getClient()).isNotNull().isInstanceOf(ClientModel.class);

    }

}
