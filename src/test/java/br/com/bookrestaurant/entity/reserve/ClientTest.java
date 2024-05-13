package br.com.bookrestaurant.entity.reserve;

import br.com.bookrestaurant.entity.reserve.exception.ReserveInvalidException;
import br.com.bookrestaurant.utilsbytests.Util;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ClientTest {
    @Test
    void testShouldPermitRegisterClientOfReserve() {
        Client client = Util.buildClient();
        assertThat(client).isNotNull();
        assertThat(client.getName()).isNotNull().isEqualTo("João");
        assertThat(client.getPhone()).isNotNull().isEqualTo(998648471);
    }

    @Test
    void testShouldThrowExceptionWhenRegisterClientOfReserve() {
        assertThatThrownBy(() -> new Client("", 998648471))
                .isInstanceOf(ReserveInvalidException.class)
                .hasMessage("Nome é obrigatório");
        assertThatThrownBy(() -> new Client("João", null))
                .isInstanceOf(ReserveInvalidException.class)
                .hasMessage("Contato é obrigatório");
    }



}
