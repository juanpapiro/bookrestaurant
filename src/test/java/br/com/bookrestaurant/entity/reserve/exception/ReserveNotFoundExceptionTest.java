package br.com.bookrestaurant.entity.reserve.exception;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ReserveNotFoundExceptionTest {

    @Test
    void testInstance() {
        ReserveNotFoundException ex = new ReserveNotFoundException();
        assertThat(ex).isNotNull().isInstanceOf(ReserveNotFoundException.class);
        assertThat(ex.getMessage()).isNotNull().isEqualTo("Reserva não localizada");
    }

}
