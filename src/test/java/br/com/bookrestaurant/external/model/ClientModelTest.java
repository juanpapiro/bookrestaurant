package br.com.bookrestaurant.external.model;

import br.com.bookrestaurant.entity.reserve.Client;
import br.com.bookrestaurant.utilsbytests.Util;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ClientModelTest {

    @Test
    void shouldCreateInstanceClientModelWithClientInConstructor() {
        Client client = Util.buildClient();
        UUID id = UUID.randomUUID();
        ClientModel clientModel = new ClientModel(client, UUID.randomUUID());

        clientModel.setReserve(new ReserveModel());
        clientModel.setCdClient(id);

        assertThat(clientModel.getName()).isNotBlank();
        assertThat(clientModel.getPhone()).isNotNull();
        assertThat(clientModel.getCdClient()).isEqualTo(id);
        assertThat(clientModel.getReserve()).isNotNull().isInstanceOf(ReserveModel.class);
    }
}
