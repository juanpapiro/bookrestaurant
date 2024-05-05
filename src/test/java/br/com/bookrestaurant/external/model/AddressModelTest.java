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
        UUID id = UUID.randomUUID();
        AddressModel addressModel = new AddressModel(address, UUID.randomUUID());
        addressModel.setRestaurant(new RestaurantModel());
        addressModel.setCdAddress(id);
        assertThat(addressModel.getStreet()).isNotBlank();
        assertThat(addressModel.getNumber()).isNotNull();
        assertThat(addressModel.getNeighborhood()).isNotBlank();
        assertThat(addressModel.getCity()).isNotBlank();
        assertThat(addressModel.getUf()).isNotBlank();
        assertThat(addressModel.getCep()).isNotBlank();
        assertThat(addressModel.getCdAddress()).isEqualTo(id);
        assertThat(addressModel.getRestaurant()).isNotNull().isInstanceOf(RestaurantModel.class);
    }

}
