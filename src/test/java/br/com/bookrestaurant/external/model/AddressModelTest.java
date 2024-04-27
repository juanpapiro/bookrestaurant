package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.restaurant.Address;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AddressModelTest {

    @Test
    void shouldCreateInstanceAddressModelWithAddressInConstructor() {
        Address address = Util.buildAddres();
        AddressModel addressModel = new AddressModel(address, UUID.randomUUID());
        assertThat(addressModel.getStreet()).isNotBlank();
        assertThat(addressModel.getNumber()).isNotNull();
        assertThat(addressModel.getNeighborhood()).isNotBlank();
        assertThat(addressModel.getCity()).isNotBlank();
        assertThat(addressModel.getUf()).isNotBlank();
        assertThat(addressModel.getCep()).isNotBlank();
    }

}
