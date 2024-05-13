package br.com.bookrestaurant.entity.restaurant;

import br.com.bookrestaurant.entity.restaurant.exception.RestaurantInvalidException;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AddressTest {

    @Test
    void testShouldPermitRegisterAddressOfRestaurant() {
        Address address = Util.buildAddres();
        assertThat(address).isNotNull();
        assertThat(address.getStreet()).isNotNull().isEqualTo("Rua Teodoro Sampaio");
        assertThat(address.getNumber()).isNotNull().isEqualTo(100);
        assertThat(address.getNeighborhood()).isNotNull().isEqualTo("Pinheiros");
        assertThat(address.getCity()).isNotNull().isEqualTo("São Paulo");
        assertThat(address.getUf()).isNotNull().isEqualTo("SP");
        assertThat(address.getCep()).isNotNull().isEqualTo("01000-000");
    }

    @Test
    void testShouldThrowExceptionWhenRegisterAddressOfRestaurant() {
        assertThatThrownBy(() -> new Address("", 100, "Pinheiros",
                "São Paulo", "SP", "01000-000"))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Nome da rua é obrigatório");

        assertThatThrownBy(() -> new Address("Rua Teodoro Sampaio", null, "Pinheiros",
                "São Paulo", "SP", "01000-000"))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Número é obrigatório");

        assertThatThrownBy(() -> new Address("Rua Teodoro Sampaio", 100, "",
                "São Paulo", "SP", "01000-000"))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Bairro é obrigatório");

        assertThatThrownBy(() -> new Address("Rua Teodoro Sampaio", 100, "Pinheiros",
                "", "SP", "01000-000"))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Cidade é obrigatória");

        assertThatThrownBy(() -> new Address("Rua Teodoro Sampaio", 100, "Pinheiros",
                "São Paulo", "", "01000-000"))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Estado é obrigatório");

        assertThatThrownBy(() -> new Address("Rua Teodoro Sampaio", 100, "Pinheiros",
                "São Paulo", "SP", ""))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Cep é obrigatório");

        assertThatThrownBy(() -> new Address("Rua Teodoro Sampaio", 100, "Pinheiros",
                "São Paulo", "SP", "12-000"))
                .isInstanceOf(RestaurantInvalidException.class)
                .hasMessage("Formato de cep inválido");
    }

}
